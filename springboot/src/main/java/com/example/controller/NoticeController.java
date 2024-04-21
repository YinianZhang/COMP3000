package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * NoticeDataTable front interface
 **/
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * Add
     */
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    /**
     * Delete
     */
    @DeleteMapping("/delete/{nid}")
    public Result deleteById(@PathVariable Integer nid) {
        noticeService.deleteById(nid);
        return Result.success();
    }

    /**
     * Batch delete
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> nids) {
        noticeService.deleteBatch(nids);
        return Result.success();
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    /**
     * Query by NID
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer nid) {
        Notice notice = noticeService.selectById(nid);
        return Result.success(notice);
    }

    /**
     * Query all
     */
    @GetMapping("/selectAll")
    public Result selectAll(Notice notice ) {
        List<Notice> list = noticeService.selectAll(notice);
        return Result.success(list);
    }

    /**
     * Page query
     */
    @GetMapping("/selectPage")
    public Result selectPage(Notice notice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notice> page = noticeService.selectPage(notice, pageNum, pageSize);
        return Result.success(page);
    }

}
