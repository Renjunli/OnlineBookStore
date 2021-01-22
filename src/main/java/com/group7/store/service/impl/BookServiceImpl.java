package com.group7.store.service.impl;


import com.group7.store.entity.book.Book;
import com.group7.store.entity.book.BookImg;
import com.group7.store.entity.book.BookSort;
import com.group7.store.entity.book.Recommend;
import com.group7.store.entity.dto.OrderBookDto;
import com.group7.store.mapper.BookMapper;
import com.group7.store.mapper.SortMapper;
import com.group7.store.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("firstVersion")
public class BookServiceImpl implements BookService {
    private static final Logger log = Logger.getLogger(BookServiceImpl.class);

    private static final String BOOK_PREFIX = "bookStore_book_";
    private static final String BOOK_LIST_PREFIX = "bookStore_bookList";
    private static final String BOOK_STOCK = "book_stock_";

    @Autowired
    BookMapper bookMapper;

    @Autowired
    SortMapper sortMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public int addBook(Book book) {
        int result = bookMapper.addBook(book);
        if (result > 0) {
            int bookId = bookMapper.getBookId(book.getisbn());
            String bookIdLog = "============bookId===============:" + bookId;
            log.info(bookIdLog);
            book.setId(bookId);
            String bookLog = "=======book：=======" + book.toString() + "============";
            log.info(bookLog);
            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);
            redisTemplate.opsForValue().set(BOOK_STOCK + book.getId(), book.getStock());
            redisTemplate.opsForZSet().add(BOOK_LIST_PREFIX, book, book.getRank());
        }
        return result;
    }

    @Override
    public List<Book> getSearchBookList(String searchParam) {
        return bookMapper.getSearchBookList(searchParam);
    }

    @Override
    public int modifyBook(Book book) {
        int result = bookMapper.modifyBook(book);
        if (result > 0) {
            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);
            redisTemplate.opsForValue().set(BOOK_STOCK + book.getId(), book.getStock());
            redisTemplate.opsForZSet().add(BOOK_LIST_PREFIX, book, book.getRank());
        }
        return result;
    }

    @Override
    public int modifyBookPut(int id, boolean put) {
        int result = bookMapper.modifyBookPut(id, put);
        if (result > 0) {
            Book book = bookMapper.getBook(id);
            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);
            redisTemplate.opsForZSet().add(BOOK_LIST_PREFIX, book, book.getRank());
        }
        return result;
    }

    @Override
    public int modifyBookRec(int id, boolean recommend) {
        int result = bookMapper.modifyBookRec(id, recommend);
        if (result > 0) {
            Book book = bookMapper.getBook(id);
            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);
            redisTemplate.opsForZSet().add(BOOK_LIST_PREFIX, book, book.getRank());
        }
        return result;
    }

    @Override
    public int modifyBookNewPro(int id, boolean newProduct) {
        int result = bookMapper.modifyBookNewPro(id, newProduct);
        if (result > 0) {
            Book book = bookMapper.getBook(id);
            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);
            redisTemplate.opsForZSet().add(BOOK_LIST_PREFIX, book, book.getRank());
        }
        return result;
    }

    @Override
    public int modifyBookStock(int id, int stockNum) {
        int result = bookMapper.modifyBookStock(id, stockNum);
        if (result > 0) {
            Book book = bookMapper.getBook(id);
            redisTemplate.opsForValue().set(BOOK_PREFIX + book.getId(), book);
            redisTemplate.opsForValue().set(BOOK_STOCK + book.getId(), book.getStock());
            redisTemplate.opsForZSet().add(BOOK_LIST_PREFIX, book, book.getRank());
        }
        return result;
    }

    @Override
    public int deleteBook(int id) {
        int result = bookMapper.deleteBook(id);
        if (result > 0) {
            if (redisTemplate.hasKey(BOOK_PREFIX + id).equals(Boolean.TRUE)) {
                redisTemplate.delete(BOOK_PREFIX + id);
            }
            if (redisTemplate.hasKey(BOOK_STOCK + id).equals(Boolean.TRUE)) {
                redisTemplate.delete(BOOK_STOCK + id);
            }
        }
        return result;
    }

    @Override
    public List<Book> getBooks() {
        return bookMapper.getBooks();
    }

    @Override
    public List<Book> getBooksByPage(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return bookMapper.getBooksByPage(start, pageSize);
    }

    @Override
    public List<OrderBookDto> getBatchBookList(int[] ids) {
        return bookMapper.getBatchBookList(ids);
    }

    @Override
    public List<OrderBookDto> getOneBookList(int[] ids) {
        return bookMapper.getOneBookList(ids);
    }

    @Override
    public List<Book> getNewPutBookList(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return bookMapper.getNewPutBookList(start, pageSize);
    }

    @Override
    public int getBookId(String isbn) {
        return bookMapper.getBookId(isbn);
    }

    @Override
    public String getBookIsbn(int id) {
        return bookMapper.getBookIsbn(id);
    }

    @Override
    public Book getBook(int id) {
        ValueOperations<String, Book> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(BOOK_PREFIX + id).equals(Boolean.TRUE)) {
            log.info("=========从缓存中读取单本图书的数据==========");
            return operations.get(BOOK_PREFIX + id);
        }
        log.info("=========从数据库中读取单本图书的数据==========");
        return bookMapper.getBook(id);
    }

    @Override
    public Book getBookDetail(String isbn) {
        return null;
    }


    @Override
    public int getPublishBookNum(String publishName) {
        return bookMapper.getPublishBookNum(publishName);
    }

    @Override
    public int getBookCount() {
        return bookMapper.getBookCount();
    }

    @Override
    public List<Book> getPublishBooks(String publishName) {
        return bookMapper.getPublishBooks(publishName);
    }


    @Override
    public int addBookImg(BookImg bookImg) {
        return bookMapper.addBookImg(bookImg);
    }

    @Override
    public int deleteBookImgOfOne(String isbn) {
        return bookMapper.deleteBookImgOfOne(isbn);
    }

    @Override
    public int deleteOneBookImg(String isbn, String imgSrc) {
        return bookMapper.deleteOneBookImg(isbn, imgSrc);
    }

    @Override
    public String getBookCover(String isbn) {
        return bookMapper.getBookCover(isbn);
    }

    @Override
    public int modifyBookImgList(String oldIsbn, String newIsbn) {
        return bookMapper.modifyBookImgList(oldIsbn, newIsbn);
    }

    @Override
    public List<String> getBookImgSrcList(String isbn) {
        return bookMapper.getBookImgSrcList(isbn);
    }

    @Override
    public int addToRecommend(Recommend recommend) {
        return bookMapper.addToRecommend(recommend);
    }

    @Override
    public int deleteFromRecommend(int bookId) {
        return bookMapper.deleteFromRecommend(bookId);
    }

    @Override
    public int modifyRecommendRank(int bookId, int rank) {
        return bookMapper.modifyRecommendRank(bookId, rank);
    }

    @Override
    public int hasExistInRec(int bookId) {
        return bookMapper.hasExistInRec(bookId);
    }

    @Override
    public List<Book> getRecommendsByPage(int page, int pageSize) {
        return bookMapper.getRecommendsByPage(page, pageSize);
    }


    @Override
    public int addToNewProduct(Recommend newProduct) {
        return bookMapper.addToNewProduct(newProduct);
    }

    @Override
    public int deleteFromNewProduct(int bookId) {
        return bookMapper.deleteFromNewProduct(bookId);
    }

    @Override
    public int modifyNewProductRank(int bookId, int rank) {
        return bookMapper.modifyNewProductRank(bookId, rank);
    }


    @Override
    public int hasExistInNew(int bookId) {
        return bookMapper.hasExistInNew(bookId);
    }

    @Override
    public List<Book> getNewProductsByPage(int page, int pageSize) {
        return bookMapper.getNewProductsByPage(page, pageSize);
    }


    //有关图书分类的操作
    @Override
    public int addBookToSort(int bookSortId, int bookId) {
        return bookMapper.addBookToSort(bookSortId, bookId);
    }

    @Override
    public int delBookFromSort(int booSortId, int bookId) {
        return bookMapper.delBookFromSort(booSortId, bookId);
    }

    @Override
    public int modifyBookSort(int bookSortId, int bookId) {
        return bookMapper.modifyBookSort(bookSortId, bookId);
    }

    @Override
    public BookSort getBookSort(int bookId) {
        return bookMapper.getBookSort(bookId);
    }


    //先得到对应分类名所有分类的id号的集合，然后按页得到一级分类的图书集合
    @Override
    public List<Book> getBooksByFirst(String sortName, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        List<Integer> item = sortMapper.getAllFirSortId(sortName);
        return bookMapper.getBooksByFirst(item, start, pageSize);
    }

    @Override
    public List<Book> getBookBySecond(int bookSortId, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        return bookMapper.getBookBySecond(bookSortId, start, pageSize);
    }

    @Override
    public int getFirstBookCount(String sortName) {
        List<Integer> item = sortMapper.getAllFirSortId(sortName);
        return bookMapper.getFirstBookCount(item);
    }

    @Override
    public int getSecondBookCount(int bookSortId) {
        return bookMapper.getSecondBookCount(bookSortId);
    }

    @Override
    public int batchDelBook(int[] idS) {
        for (int i = 0; i < idS.length; i++) {
            if (bookMapper.deleteBook(idS[i]) < 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int batchPutBook(int[] idS, boolean put) {
        for (int i = 0; i < idS.length; i++) {
            if (bookMapper.modifyBookPut(idS[i], put) < 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int batchRecBook(int[] idS, boolean recommend) {
        Recommend r = new Recommend();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        r.setAddTime(timestamp);
        if (recommend) {
            for (int i = 0; i < idS.length; i++) {
                int isExistInRec = bookMapper.hasExistInRec(idS[i]);
                if (isExistInRec == 0) {
                    r.setBookId(idS[i]);
                    bookMapper.addToRecommend(r);
                }
                if (bookMapper.modifyBookRec(idS[i], true) < 1) {
                    return 0;
                }
            }
        } else {
            for (int i = 0; i < idS.length; i++) {
                bookMapper.modifyBookRec(idS[i], false);
                bookMapper.deleteFromRecommend(idS[i]);
            }
        }
        return 1;
    }

    @Override
    public int batchNewProBook(int[] idS, boolean newProduct) {
        Recommend r = new Recommend();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        r.setAddTime(timestamp);
        if (newProduct) {
            for (int i = 0; i < idS.length; i++) {
                int isExistInNew = bookMapper.hasExistInNew(idS[i]);
                if (isExistInNew == 0) {
                    r.setBookId(idS[i]);
                    bookMapper.addToNewProduct(r);
                }
                if (bookMapper.modifyBookNewPro(idS[i], true) < 1) {
                    return 0;
                }
            }
        } else {
            for (int i = 0; i < idS.length; i++) {
                bookMapper.modifyBookNewPro(idS[i], false);
                bookMapper.deleteFromNewProduct(idS[i]);
            }
        }
        return 1;
    }
}
