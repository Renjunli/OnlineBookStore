package com.group7.store.service.impl;

import com.group7.store.entity.book.Book;
import com.group7.store.entity.dto.*;
import com.group7.store.entity.order.Expense;
import com.group7.store.entity.order.Order;
import com.group7.store.entity.order.OrderDetail;
import com.group7.store.entity.order.OrderStatusEnum;
import com.group7.store.mapper.BookMapper;
import com.group7.store.mapper.ExpenseMapper;
import com.group7.store.mapper.OrderMapper;
import com.group7.store.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private static final Logger log = Logger.getLogger(OrderServiceImpl.class);

    private static final String STOCK_PREFIX = "stock_";//这个用来设置锁
    private static final String BOOK_STOCK = "book_stock_";//这个用来存储库存的缓存
    private static final String BOOK_PREFIX = "bookStore_book_";//这个用来存储单个图书的数据
//    private static final String BOOKLIST_PREFIX = "bookStore_bookList";//图书集合中数据
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ExpenseMapper expenseMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    RedisTemplate redisTemplate;

    public String initOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            result.append(random.nextInt(10));
        }
        return newDate + result.toString();
    }


    @Transactional
    @Override
    public boolean addOrder(OrderInitDto orderInitDto) {
        Order order = new Order();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String orderId = initOrderId();
        order.setOrderId(orderId);
        String msgInfo1 = "============orderInitDto.getAccount():===========" + orderInitDto.getAccount() + "============";
        log.info(msgInfo1);
        order.setAccount(orderInitDto.getAccount());
        order.setAddressId(orderInitDto.getAddress().getId());//收货地址编号
        order.setOrderTime(timestamp);
        order.setOrderStatus(OrderStatusEnum.SUBMIT.getName());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (OrderBookDto orderBookDto : orderInitDto.getBookList()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBookId(orderBookDto.getId());
            orderDetail.setNum(orderBookDto.getNum());
            orderDetail.setPrice(orderBookDto.getPrice());
            orderDetail.setOrderId(orderId);
            orderDetailList.add(orderDetail);
            String msgInfo2 = "=====orderDetail.toString()=====" + orderDetail.toString();
            log.info(msgInfo2);
            String clientId = UUID.randomUUID().toString();
            try {

                if (!redisTemplate.opsForValue().setIfAbsent(STOCK_PREFIX + orderBookDto.getId(), clientId, 10, TimeUnit.SECONDS).equals(Boolean.TRUE)) {
                    return false;//获取分布式锁出错了！
                }
                if (redisTemplate.hasKey(BOOK_STOCK + orderBookDto.getId()).equals(Boolean.TRUE)) {//如果缓存中有库存数据
                    int stock = Integer.parseInt((String) redisTemplate.opsForValue().get(BOOK_STOCK + orderBookDto.getId()));
                    if (stock > orderBookDto.getNum()) {
                        int realStock = stock - orderBookDto.getNum();
                        redisTemplate.opsForValue().set(BOOK_STOCK + orderBookDto.getId(), realStock);//更新库存缓存
                        ValueOperations<String, Book> operations = redisTemplate.opsForValue();
                        if (redisTemplate.hasKey(BOOK_PREFIX + orderBookDto.getId()).equals(Boolean.TRUE)) {
                            String msgInfo3 = "=========从缓存中读取数据==========";
                            log.info(msgInfo3);
                            Book book = operations.get(BOOK_PREFIX + orderBookDto.getId());
                            if (book == null) {
                                return false;
                            }
                            book.setStock(realStock);
                            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);//更新图书缓存中的数据
                        }
                        Book book = bookMapper.getBook(orderBookDto.getId());
                        redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);//更新图书缓存中的数据
//                        redisTemplate.opsForZSet().remove(book);//删除集合中原来的图书数据
                        book.setStock(realStock);
//                        redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());//添加新的图书数据到缓存集合中去
                        try {
                            String msgInfo4 = "================开始减库存====================";
                            log.info(msgInfo4);
                            int update = bookMapper.modifyBookStock(orderBookDto.getId(), orderBookDto.getNum());//减去库存
                            String msgInfo5 = "==============减去库存=====================";
                            log.info(msgInfo5);
                        } catch (Exception e) {
                            redisTemplate.opsForValue().set(BOOK_STOCK + orderBookDto.getId(), stock);//恢复缓存中的库存数量，避免少买
//                            redisTemplate.opsForZSet().remove(book);//删除集合中原来的图书数据
                            book.setStock(stock);
                            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);//更新图书缓存中的数据
//                            redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());//添加新的图书数据到缓存集合中去
                        }
                        String msgInfo6 = "=============减去库存没有问题======================";
                        log.info(msgInfo6);
                    } else {
                        throw new Exception("=====库存不足========");
                    }
                } else {
                    int update = bookMapper.modifyBookStock(orderBookDto.getId(), orderBookDto.getNum());//减去库存
                    if (update < 1) {
                        String msgInfo7 = "=====库存不足========";
                        log.info(msgInfo7);
                        return false;
                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                if (clientId.equals(redisTemplate.opsForValue().get(STOCK_PREFIX + orderBookDto.getId()))) {
                    redisTemplate.delete(STOCK_PREFIX + orderBookDto.getId());
                }
            }
        }
        for (int i = 0; i < orderDetailList.size(); i++) {
            String msg1 = "=====orderDetailList[i]=====" + orderDetailList.get(i);
            log.info(msg1);
        }

        orderMapper.addOrder(order);//添加总的订单

        orderMapper.batchAddOrderDetail(orderDetailList);//批量添加订单明细

        Expense expense = orderInitDto.getExpense();
        expense.setOrderId(orderId);
        expenseMapper.addExpense(expense);//订单订单费用到费用表中
        String msg = "===========添加订单成功==============";
        log.info(msg);
        return true;
    }

    @Override
    public int delOrder(int id) {
        return orderMapper.delOrder(id);
    }

    @Override
    public int userDelOrder(int id) {
        Order order = new Order();
        order.setId(id);
        order.setBeUserDelete(true);
        return orderMapper.modifyOrder(order);
    }

    @Override
    public int batchDelOrder(List<Integer> item) {
        return orderMapper.batchDelOrder(item);
    }

    @Override
    public int modifyOrderStatus(int id, String orderStatus) {
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(orderStatus);
        return orderMapper.modifyOrder(order);
    }

    @Transactional
    @Override
    public int deliverBook(int id, int logisticsCompany, String logisticsNum) {
        int result = orderMapper.modifyLogistics(id, logisticsCompany, logisticsNum);
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus("已发货");
        orderMapper.modifyOrder(order);
        return 1;
    }

    @Override
    public OrderDto findOrderDto(int id) {
        OrderDto orderDto;
        orderDto = orderMapper.findOrderDto(id);
        List<OrderDetailDto> orderDetailDtoList = orderMapper.findOrderDetailDtoList(orderDto.getOrderId());
        for (int i = 0; i < orderDetailDtoList.size(); i++) {
            String msgLog = "=======" + orderDetailDtoList.get(i).toString() + "=====";
            log.info(msgLog);
        }
        orderDto.setOrderDetailDtoList(orderDetailDtoList);
        return orderDto;
    }

    @Override
    public List<OrderDetailDto> findOrderDetailDtoList(String orderId) {
        return orderMapper.findOrderDetailDtoList(orderId);
    }

    @Override
    public List<OrderDto> orderDtoList(String userId, int pageNum, int pageSize, String orderStatus, boolean beUserDelete) {
        pageNum = (pageNum - 1) * pageSize;
        return orderMapper.orderDtoList(userId, pageNum, pageSize, orderStatus, beUserDelete);
    }

    @Override
    public int count(String userId, String orderStatus, boolean beUserDelete) {
        return orderMapper.count(userId, orderStatus, beUserDelete);
    }

    @Override
    public List<OrderStatistic> getOrderStatistic(String beginDate, String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(beginDate);
        Date date1 = format.parse(endDate);

        return orderMapper.getOrderStatistic(new Timestamp(date.getTime()), new Timestamp(date1.getTime()));
    }
}
