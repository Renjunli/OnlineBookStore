package com.group7.store.controller;


import com.group7.store.entity.dto.*;
import com.group7.store.entity.order.Expense;
import com.group7.store.entity.user.Address;
import com.group7.store.service.AddressService;
import com.group7.store.service.BookService;
import com.group7.store.service.CartService;
import com.group7.store.service.OrderService;
import com.group7.store.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;


@Controller
@ResponseBody
public class OrderController {

    @Autowired
    @Qualifier("firstVersion")
    BookService bookService;

    @Autowired
    @Qualifier("firstCart")
    CartService cartService;

    @Autowired
    @Qualifier("firstAddress")
    AddressService addressService;

    @Autowired
    @Qualifier("orderService")
    OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    /**
     * 初始化订单，得到用户购买的图书集合，得到费用信息，并返回前端费用信息和图书集合
     *
     * @param ids  用户预购买的时候的图书id集合
     * @param from from用来标记下订单是从哪个页面操作的 0代表详情页 1代表购物车
     * @return
     */
    @GetMapping("/initOrder")
    public Map<String, Object> initOrder(@RequestParam(value = "ids") int[] ids,
                                         @RequestParam(value = "from") int from,
                                         @RequestParam(value = "account") String account) {
        for (int i = 0; i < ids.length; i++) {
            String idLog = "===ids[i]========" + ids[i] + "==============";
            log.info(idLog);
        }
        Map<String, Object> map = new HashMap<>();
        Expense expense = new Expense();
        OrderInitDto orderInitDto = new OrderInitDto();
        List<OrderBookDto> batchBookList;
        if (from == 1) {//从购物车点击提交的
            batchBookList = bookService.getBatchBookList(ids);
            for (int i = 0; i < batchBookList.size(); i++) {
                int bookCount = cartService.getBookCount(account, batchBookList.get(i).getId());
                batchBookList.get(i).setNum(bookCount);
                String bookName = String.format("====batchBookList.get(i).getNum():======%s======", batchBookList.get(i).getNum());
                log.info(bookName);
            }
            cartService.delBatchProduct(account, ids);//删除购物车中的图书
        } else {//从详情页点击提交的
            batchBookList = bookService.getOneBookList(ids);
            batchBookList.get(0).setNum(1);
        }
        for (int i = 0; i < batchBookList.size(); i++) {
            String img = bookService.getBookCover(batchBookList.get(i).getIsbn());
            batchBookList.get(i).setCoverImg(img);
        }

        Double productTotalMoney = 0.0;
        for (OrderBookDto orderBookDto : batchBookList) {
            productTotalMoney = productTotalMoney + orderBookDto.getPrice() * orderBookDto.getNum();//得到订单总价
        }
        expense.setProductTotalMoney(productTotalMoney);//商品总价
        expense.setFreight(0);
        expense.setCoupon(0);
        expense.setActivityDiscount(0);
        expense.setAllPrice(productTotalMoney);//订单总金额
        expense.setFinallyPrice(productTotalMoney);//最终实付金额
        List<Address> addressList = addressService.addressList(account);//得到某个用户的地址列表
        orderInitDto.setAddressList(addressList);
        orderInitDto.setBookList(batchBookList);
        orderInitDto.setExpense(expense);
        orderInitDto.setAccount(account);
        String accountlog = "============account:===========" + account + "============";
        log.info(accountlog);
        map.put("orderInitDto", orderInitDto);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 添加订单
     *
     * @param orderInitDto
     * @return
     */
    @PostMapping("/addOrder")
    public Map<String, Object> addOrder(@RequestBody OrderInitDto orderInitDto) {
        if (!orderService.addOrder(orderInitDto)) {
            return ResultUtil.resultCode(500, "提交订单失败");
        }
        return ResultUtil.resultCode(200, "提交订单成功");
    }


    /**
     * 得到管理员可以查看的订单信息列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getAdminOrderList")
    public Map<String, Object> egtOrderList(@RequestParam("page") int page,
                                            @RequestParam("pageSize") int pageSize) {
        log.info("=========请求到达获取订单接口===========");
        List<OrderDto> orderDtoList = orderService.orderDtoList("", page, pageSize, "", false);
        Map<String, Object> map = new HashMap<>();
        map.put("orderDtoList", orderDtoList);
        int total = orderService.count("", "", false);
        map.put("total", total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 得到某个订单的全部明细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getOrderDto")
    public Map<String, Object> getOrderDto(@RequestParam("id") int id) {
        OrderDto orderDto = orderService.findOrderDto(id);
        List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailDtoList(orderDto.getOrderId());
        for (int i = 0; i < orderDetailDtoList.size(); i++) {
            String img = bookService.getBookCover(orderDetailDtoList.get(i).getBook().getisbn());
            orderDetailDtoList.get(i).getBook().setCoverImg(img);
            String orderDetailDtoListLog = "=======orderDetailDtoList.size():=====" + orderDetailDtoList.size() + "============";
            log.info(orderDetailDtoListLog);
        }
        orderDto.setOrderDetailDtoList(orderDetailDtoList);
        Map<String, Object> map = new HashMap<>();
        map.put("orderDto", orderDto);
        return ResultUtil.resultSuccess(map);
    }


    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    @GetMapping("/delOrder")
    public Map<String, Object> delOrder(@RequestParam("id") int id) {
        String idLog = "=============" + id + "=================";
        log.info(idLog);
        if (orderService.delOrder(id) > 0) {
            return ResultUtil.resultCode(200, "删除订单成功");
        }
        return ResultUtil.resultCode(500, "删除订单失败");
    }

    @GetMapping("/deliverOrder")
    public Map<String, Object> delOrdr(@RequestParam("id") int id,
                                       @RequestParam("logisticsCompany") int logisticsCompany,
                                       @RequestParam("logisticsNum") String logisticsNum) {
        String idLog = "=============" + id + "=================";
        log.info(idLog);
        if (orderService.deliverBook(id, logisticsCompany, logisticsNum) > 0) {
            return ResultUtil.resultCode(200, "发货成功");
        }
        return ResultUtil.resultCode(500, "发货失败");
    }

    @GetMapping("/getUserOrderList")
    public Map<String, Object> getUserOrderList(@RequestParam("account") String account,
                                                @RequestParam("page") int page,
                                                @RequestParam("pageSize") int pageSize,
                                                @RequestParam("orderStatus") String orderStatus,
                                                @RequestParam("beUserDelete") boolean beUserDelete) {
        String orderStatusAndbeUserDeleteLog = "=========orderStatus,beUserDelete===========:" + orderStatus + " " + beUserDelete + "=========";
        log.info(orderStatusAndbeUserDeleteLog);
        List<OrderDto> orderDtoList = orderService.orderDtoList(account, page, pageSize, orderStatus, beUserDelete);
        for (int i = 0; i < orderDtoList.size(); i++) {
            List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailDtoList(orderDtoList.get(i).getOrderId());
            List<String> coverImgList = new ArrayList<>();
            for (int j = 0; j < orderDetailDtoList.size() && j < 5; j++) {
                String orderDetailDtoListLog = "======orderDetailDtoList.get(j)====:" + orderDetailDtoList.get(j) + "=========";
                log.info(orderDetailDtoListLog);
                String img = bookService.getBookCover(orderDetailDtoList.get(j).getBook().getisbn());
                coverImgList.add(img);
            }
            String coverImgListLog = "=====coverImgList.size()=====" + coverImgList.size() + "===================";
            log.info(coverImgListLog);
            orderDtoList.get(i).setCoverImgList(coverImgList);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("orderDtoList", orderDtoList);
        int total = orderService.count(account, orderStatus, beUserDelete);
        map.put("total", total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 修改订单的状态
     *
     * @param id
     * @param orderStatus
     * @return
     */
    @GetMapping("/modifyOrderStatus")
    public Map<String, Object> modifyOrderStatus(@RequestParam("id") int id,
                                                 @RequestParam("orderStatus") String orderStatus) {
        String idLog = "========确认收货====" + id;
        log.info(idLog);
        if (orderService.modifyOrderStatus(id, orderStatus) > 0) {
            return ResultUtil.resultCode(200, "操作成功");
        }
        return ResultUtil.resultCode(500, "操作失败");
    }


    /**
     * 返回日期筛选后的订单数据
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    @GetMapping("/order/date")
    public Map<String, Object> dateFilter(@RequestParam("beginDate") String beginDate,
                                          @RequestParam("endDate") String endDate) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        List<OrderStatistic> orderStatistic = orderService.getOrderStatistic(beginDate, endDate);
        map.put("orderStatistic", orderStatistic);
        return ResultUtil.resultSuccess(map);
    }


}
