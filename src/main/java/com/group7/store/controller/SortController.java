package com.group7.store.controller;

import com.group7.store.entity.book.BookSort;
import com.group7.store.entity.dto.SortResponse;
import com.group7.store.service.SortService;
import com.group7.store.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * 图书分类管理
 * jcz
 * 2021.1.18
 */


@Controller
@ResponseBody
@RequestMapping(value = "/sort")
public class SortController {

    @Autowired
    @Qualifier("firstSort")
    SortService sortService;
    private static final Logger log = LoggerFactory.getLogger(SortController.class);


    /**
     * 添加图书的分类
     * 2021/1/18
     * jcz
     * @param bookSort
     * @return
     */
    @PostMapping("/addBookSort")
    public Map<String, Object> addBookSort(@RequestBody BookSort bookSort) {
        if (sortService.addSort(bookSort) > 0) {
            String msg = "图书分类添加成功";
            log.info(msg);
            return ResultUtil.resultCode(200, "添加图书分类成功");
        }
        return ResultUtil.resultCode(500, "添加图书分类失败");
    }

    /**
     * 查询图书的分类
     * 2021/1/18
     * jcz
     * @param upperName
     * @param sortName
     * @return
     */
    @GetMapping("/getBookSort")
    public Map<String, Object> getBookSort(@RequestParam(value = "upperName") String upperName,
                                           @RequestParam(value = "sortName") String sortName) {
        BookSort bookSort  = sortService.getBookSort(upperName, sortName);
        Map<String, Object> map = new HashMap<>();
        map.put("bookSort", bookSort);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 修改图书的分类
     * 2021/1/18
     * jcz
     * @param bookSort
     * @return
     */
    @PostMapping("/modifyBookSort")
    public Map<String, Object> modifyBookSort(@RequestBody BookSort bookSort) {
        if ("无".equals(bookSort.getUpperName())) {
            //如果修改后无上一级
            //得到修改分类原来的值
            BookSort bookSort1 = sortService.getBookSortById(bookSort.getId());
            if (!bookSort1.getSortName().equals(bookSort.getSortName())) {
                //如果修改分类的的原来的值不等于当前值
                sortService.modifySortUpperName(bookSort1.getSortName(), bookSort.getSortName());
            }
        }
        if (sortService.modifySort(bookSort) > 0) {
            return ResultUtil.resultCode(200, "修改图书分类成功");
        }
        return ResultUtil.resultCode(500, "修改图书分类失败");
    }

    /**
     * 分页查询一级分类列表
     * 2021/1/18
     * jcz
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getFirstSortList")
    public Map<String, Object> getFirstSortList(@RequestParam(value = "page") int page,
                                                @RequestParam(value = "pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<BookSort> bookSortList = sortService.getFirstSorts(page, pageSize);
        map.put("bookSortList", bookSortList);
        int total = sortService.getFirstCount();
        map.put("total", total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 分页查询二级分类列表
     * 2021/1/18
     * jcz
     * @param upperName
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getSecondSortList")
    public Map<String, Object> getSecondSortList(@RequestParam(value = "upperName") String upperName,
                                                 @RequestParam(value = "page") int page,
                                                 @RequestParam(value = "pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<BookSort> bookSortList = sortService.getSecondSorts(upperName, page, pageSize);
        map.put("bookSortList", bookSortList);
        int total = sortService.getSecondCount(upperName);
        map.put("total", total);
        return ResultUtil.resultSuccess(map);
    }


    /**
     * 查询所有一级分类
     * 2021/1/18
     * jcz
     * @return
     */
    @GetMapping("/getUpperNames")
    public Map<String, Object> getUpperSorts() {
        Map<String, Object> map = new HashMap<>();
        List<String> upperNames = sortService.getUpperSorts();
        upperNames.add("无");
        int size = upperNames.size();
        Collections.swap(upperNames, size - 1, 0);
        map.put("upperNames", upperNames);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 获取所有的一级分类和二级分类，封装后放给前端
     * 2021/1/18
     * jcz
     * @return
     */
    @GetMapping("/getBookSortList")
    public Map<String, Object> getBookSortList() {
        Map<String, Object> map = new HashMap<>();
        List<SortResponse> sortResponseList = new ArrayList<>();
        //查询所有一级分类
        List<BookSort> bookSortList = sortService.getAllFirSorts();
        for (int i = 0; i < bookSortList.size(); i++) {
            List<BookSort> children = sortService.getSecondSortList(bookSortList.get(i).getSortName());
            SortResponse sortResponse = new SortResponse();
            sortResponse.setUpperSort(bookSortList.get(i));
            sortResponse.setChildren(children);
            sortResponseList.add(sortResponse);
        }
        map.put("sortResponseList", sortResponseList);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 删除一级分类
     * 2021/1/18
     * jcz
     * @param sortName
     * @return
     */
    @GetMapping("/delFirstSort")
    public Map<String, Object> deleteSort(@RequestParam(value = "sortName") String sortName) {
        if (sortService.deleteFirSort(sortName) > 0) {
            return ResultUtil.resultCode(200, "删除分类成功");
        }
        return ResultUtil.resultCode(500, "删除分类失败");
    }

    /**
     * 删除二级分类
     * 2021/1/18
     * jcz
     * @param sortName
     * @param upperName
     * @return
     */
    @GetMapping("/delSecondSort")
    public Map<String, Object> deleteSecSort(@RequestParam(value = "sortName") String sortName,
                                             @RequestParam(value = "upperName") String upperName) {
        if (sortService.deleteSort(upperName, sortName) > 0) {
            return ResultUtil.resultCode(200, "删除分类成功");
        }
        return ResultUtil.resultCode(500, "删除分类失败");
    }


}
