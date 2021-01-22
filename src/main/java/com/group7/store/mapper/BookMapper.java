package com.group7.store.mapper;

import com.group7.store.entity.book.Book;
import com.group7.store.entity.book.BookImg;
import com.group7.store.entity.book.BookSort;
import com.group7.store.entity.book.Recommend;
import com.group7.store.entity.dto.OrderBookDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/19
 */
@Repository
public interface BookMapper {
    //对于book表的操作
    int addBook(Book book);

    int modifyBook(Book book);

    /**
     * 修改图书是否为上下架
     *
     * @param id
     * @param put
     * @return
     */
    int modifyBookPut(int id, boolean put);

    /**
     * 修改图书是否为推荐图书
     *
     * @param id
     * @param recommend
     * @return
     */
    int modifyBookRec(int id, boolean recommend);

    /**
     * 修改图书是否为新品
     *
     * @param id
     * @param newProduct
     * @return
     */
    int modifyBookNewPro(int id, boolean newProduct);

    /**
     * 减库存stockNum
     *
     * @param id
     * @param stockNum
     * @return
     */
    int modifyBookStock(int id, int stockNum);

    /**
     * 删除图书
     *
     * @param id
     * @return
     */
    int deleteBook(int id);

    /**
     * 得到所有图书
     *
     * @return
     */
    List<Book> getBooks();

    /**
     * 按页得到图书集合
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<Book> getBooksByPage(int page, int pageSize);

    /**
     * 按页得到新上架的图书集合
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<Book> getNewPutBookList(int page, int pageSize);

    /**
     * 根据ids数组，得到对应的图书集合
     *
     * @param ids
     * @return
     */
    List<OrderBookDto> getBatchBookList(int[] ids);

    /**
     * 根据ids数组，得到对应的图书集合
     *
     * @param ids
     * @return
     */
    List<OrderBookDto> getOneBookList(int[] ids);

    int getBookId(String isbn);

    String getBookIsbn(int id);

    /**
     * 获取图书的所有
     *
     * @param id
     * @return
     */
    Book getBook(int id);

    /**
     * 获得图书用于后台页表展示的信息(除去图书详情页和部分图书相册)
     *
     * @param isbn
     * @return
     */
    Book getBookDetail(String isbn);

    /**
     * 得到某一出版社的图书的数量
     *
     * @param publishName
     * @return
     */
    int getPublishBookNum(String publishName);

    /**
     * 得到图书的数量
     *
     * @return
     */
    int getBookCount();

    /**
     * 得到某一个出版社的所有图书
     *
     * @param publishName
     * @return
     */
    List<Book> getPublishBooks(String publishName);


    /**
     * 对于bookimg表的操作
     *
     * @param bookImg
     * @return
     */
    int addBookImg(BookImg bookImg);

    /**
     * 删除某本书的全部图片
     *
     * @param isbn
     * @return
     */
    int deleteBookImgOfOne(String isbn);

    int deleteOneBookImg(String isbn, String imgSrc);

    /**
     * 得到书的封面图
     *
     * @param isbn
     * @return
     */
    String getBookCover(String isbn);

    /**
     * 当某本图书的isbn好改变了，需要修改与该图书相关联的相册isbn号
     *
     * @param oldIsbn
     * @param newIsbn
     * @return
     */
    int modifyBookImgList(String oldIsbn, String newIsbn);

    /**
     * 按isbn号得到某本图书的所有图书集合
     *
     * @param isbn
     * @return
     */
    List<String> getBookImgSrcList(String isbn);

    //对于推荐图书的操作
    int addToRecommend(Recommend recommend);

    int deleteFromRecommend(int bookId);

    int modifyRecommendRank(int bookId, int rank);

    int modifyRecommend(Recommend recommend);

    int hasExistInRec(int bookId);

    List<Book> getRecommendsByPage(int page, int pageSize);

    //对于新品推荐的操作
    int addToNewProduct(Recommend newProduct);

    int deleteFromNewProduct(int bookId);

    int modifyNewProductRank(int bookId, int rank);

    int modifyNewProduct(Recommend newProduct);

    int hasExistInNew(int bookId);

    List<Book> getNewProductsByPage(int page, int pageSize);


    //添加图书到分类
    int addBookToSort(int bookSortId, int bookId);

    int delBookFromSort(int booSortId, int bookId);

    int modifyBookSort(int bookSortId, int bookId);

    /**
     * 得到某本书的分类名
     *
     * @param bookId
     * @return
     */
    BookSort getBookSort(int bookId);

    /**
     * 得到级别一的所有分类书籍
     *
     * @param list
     * @param page
     * @param pageSize
     * @return
     */
    List<Book> getBooksByFirst(List<Integer> list, int page, int pageSize);

    /**
     * 得到级别二的所有分类图书
     *
     * @param bookSortId
     * @param page
     * @param pageSize
     * @return
     */
    List<Book> getBookBySecond(int bookSortId, int page, int pageSize);

    /**
     * 得到一级分类图书的数量
     *
     * @param list
     * @return
     */
    int getFirstBookCount(List<Integer> list);

    /**
     * 得到二级分类图书的数量
     *
     * @param bookSortId
     * @return
     */
    int getSecondBookCount(int bookSortId);

    /**
     * 按照书名、作者、ISBN、出版社获取图书
     * @param searchParam
     * @return
     */
    List<Book> getSearchBookList(String searchParam);
}
