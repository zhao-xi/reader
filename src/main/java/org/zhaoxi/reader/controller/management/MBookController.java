package org.zhaoxi.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.zhaoxi.reader.entity.Book;
import org.zhaoxi.reader.exception.BussinessException;
import org.zhaoxi.reader.service.BookService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;

    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book");
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/"; // 得到上传目录
        String fileName = UUID.randomUUID().toString().replace("-", ""); // 生成随机文件名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); // 获得扩展名
        file.transferTo(new File(uploadPath + fileName + suffix)); // 保存文件到upload目录
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            Document doc = Jsoup.parse(book.getDescription()); //解析图书详情（是一段HTML）
            Element img = doc.select("img").first(); //获取第一张图
            book.setCover(img.attr("src"));
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if(page == null) page = 1;
        if(limit == null) limit = 10;
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());
        result.put("count", pageObject.getTotal());
        return result;
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        Book book = bookService.selectById(bookId);
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);
        return result;
    }

    /**
     * 更新图书数据
     * @param book
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {
        Map result = new HashMap();
        try {
            // 传入的book不包含评分信息，所以先获得原始数据库记录
            Book rawBook = bookService.selectById(book.getBookId());
            rawBook.setBookName((book.getBookName()));
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }
}
