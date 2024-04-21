package com.example.controller;

import com.example.common.Result;
import com.example.entity.Goods;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * GoodsDataTable front interface
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * Add
     */
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods) {
        goodsService.add(goods);
        return Result.success();
    }

    /**
     * Delete
     */
    @DeleteMapping("/delete/{gid}")
    public Result deleteById(@PathVariable Integer gid) {
        goodsService.deleteById(gid);
        return Result.success();
    }

    /**
     * Batch delete
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> gids) {
        goodsService.deleteBatch(gids);
        return Result.success();
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return Result.success();
    }

    /**
     * Query by ID
     */
    @GetMapping("/selectById/{gid}")
    public Result selectById(@PathVariable Integer gid) {
        Goods goods = goodsService.selectById(gid);
        return Result.success(goods);
    }

    /**
     * Query all
     */
    @GetMapping("/selectAll")
    public Result selectAll(Goods goods ) {
        List<Goods> list = goodsService.selectAll(goods);
        return Result.success(list);
    }

    /**
     * Page query
     */
    @GetMapping("/selectPage")
    public Result selectPage(Goods goods,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Goods> page = goodsService.selectPage(goods, pageNum, pageSize);
        return Result.success(page);
    }

}
