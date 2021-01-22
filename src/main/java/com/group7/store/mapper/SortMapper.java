package com.group7.store.mapper;

import com.group7.store.entity.book.BookSort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SortMapper {

    //添加图书分类
    int addSort(BookSort bookSort);

    int deleteSort(String upperName, String sortName);

    int deleteFirSort(String sortName);

    //修改图书分类
    int modifySort(BookSort bookSort);

    //修改上一级的分类名称后，需要修改下一级别的所有分类的的父级
    int modifySortUpperName(String oldUpperName, String newUpperName);

    //通过分类的一级分类名和二级分类名查找分类
    BookSort getBookSort(String upperName, String sortName);

    //通过分类的一级分类名和二级分类名查找分类的id
    int getBookSortId(String upperName, String sortName);

    //通过分类的id号查询
    BookSort getBookSortById(int id);

    //按页得到一级分类
    List<BookSort> getFirstSorts(int page, int pageSize);

    //得到所有的一级分类
    List<BookSort> getAllFirSorts();

    //按页得到二级分类
    List<BookSort> getSecondSorts(String upperName, int page, int pageSize);

    //得到对应父级父类下的所有子分类
    List<BookSort> getSecondSortList(String upperName);

    //得到一级分类的所有分类名
    List<String> getUpperSorts();

    //得到某一级分类的所有子类及其自己的id号集
    List<Integer> getAllFirSortId(String sortName);

    //按页得到某一级分类的所有子类id号集
    List<Integer> getSecSortIdPage(String sortName, int page, int pageSize);

    //得到一级分类的数量
    int getFirstCount();

    //得到二级分类的数量
    int getSecondCount(String upperName);


}
