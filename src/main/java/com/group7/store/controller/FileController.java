package com.group7.store.controller;


import com.group7.store.entity.book.BookImg;
import com.group7.store.service.BookService;
import com.group7.store.util.OssUploadImage;
import com.group7.store.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    @Qualifier("firstVersion")
    BookService bookService;

    @Autowired
    OssUploadImage ossUploadImage;

    /**
     * 上传图书图片
     *
     * @param map
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadBookImg")
    public Map<String, Object> upload(@RequestParam Map<String, String> map, @RequestParam("file") MultipartFile file) {
        System.out.println("上传图片起作用！");
        Map<String, Object> m = new HashMap<>();
        String isbn = "";
        if (map.size() > 0) {
            try {
                isbn = map.get("isbn").toString();
            } catch (Exception e) {
                return ResultUtil.resultCode(500, "上传图片失败");
            }
        }
        String imageSrc = ossUploadImage.uploadImg(file);
        BookImg bookImg = new BookImg();
        bookImg.setImgSrc(imageSrc);
        bookImg.setIsbn(isbn);
        if (bookService.addBookImg(bookImg) > 0) {
            return ResultUtil.resultCode(200, "上传图片成功");
        }
        return ResultUtil.resultCode(500, "上传图片失败");
    }

    /**
     * 删除某张图片
     *
     * @param url
     * @param isbn
     * @return
     */
    @GetMapping("/delOneImg")
    public Map<String, Object> delImg(@RequestParam(value = "url") String url,
                                      @RequestParam(value = "isbn") String isbn) {
        System.out.println("删除图片");
        System.out.println("bookId:" + isbn);
        if (bookService.deleteOneBookImg(isbn, url) > 0) {
            return ResultUtil.resultCode(200, "删除图片成功");
        }
        return ResultUtil.resultCode(200, "删除图片失败");
    }

}
