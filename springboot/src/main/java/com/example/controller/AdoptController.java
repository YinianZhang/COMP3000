package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.common.enums.AdoptAstatusEnum;
import com.example.entity.Adopt;
import com.example.service.AdoptService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * AdoptDataTable front interface
 **/
@RestController
@RequestMapping("/adopt")
public class AdoptController {

    @Resource
    private AdoptService adoptService;

    /**
     * Add
     */
    @PostMapping("/add")
    public Result add(@RequestBody Adopt adopt) {
        adopt.setAtime(DateUtil.now());
        adopt.setAstatus(AdoptAstatusEnum.ADOPT_YES.status);
        adoptService.add(adopt);
        return Result.success();
    }

    /**
     * Delete
     */
    @DeleteMapping("/delete/{aid}")
    public Result deleteById(@PathVariable Integer aid) {
        adoptService.deleteById(aid);
        return Result.success();
    }

    /**
     * Batch delete
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> aids) {
        adoptService.deleteBatch(aids);
        return Result.success();
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Adopt adopt) {
        adoptService.updateById(adopt);
        adoptService.updateById1(adopt);
        return Result.success();
    }

    /**
     * Query by ID
     */
    @GetMapping("/selectById/{aid}")
    public Result selectById(@PathVariable Integer aid) {
        Adopt adopt = adoptService.selectById(aid);
        return Result.success(adopt);
    }

    /**
     * Query all
     */
    @GetMapping("/selectAll")
    public Result selectAll(Adopt adopt ) {
        List<Adopt> list = adoptService.selectAll(adopt);
        return Result.success(list);
    }

    /**
     * Page query
     */
    @GetMapping("/selectPage")
    public Result selectPage(Adopt adopt,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Adopt> page = adoptService.selectPage(adopt, pageNum, pageSize);
        return Result.success(page);
    }

}
