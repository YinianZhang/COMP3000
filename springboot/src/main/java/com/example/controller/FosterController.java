package com.example.controller;

import com.example.common.Result;
import com.example.entity.Foster;
import com.example.service.FosterService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * FosterDataTable front interface
 **/
@RestController
@RequestMapping("/foster")
public class FosterController {

    @Resource
    private FosterService fosterService;

    /**
     * Add
     */
    @PostMapping("/add")
    public Result add(@RequestBody Foster foster) {
        fosterService.add(foster);
        return Result.success();
    }

    /**
     * Delete
     */
    @DeleteMapping("/delete/{fid}")
    public Result deleteById(@PathVariable Integer fid) {
        fosterService.deleteById(fid);
        return Result.success();
    }

    /**
     * Batch delete
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> fids) {
        fosterService.deleteBatch(fids);
        return Result.success();
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Foster foster) {
        fosterService.updateById(foster);
        return Result.success();
    }

    /**
     * Query by ID
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer fid) {
        Foster foster = fosterService.selectById(fid);
        return Result.success(foster);
    }
    @GetMapping("/selectByIdAndUser")
    public Result selectByNameAndUser(String pet_name,int userId) {
        List<Foster> fosters = fosterService.selectByNameAndUser("%"+pet_name+"%",userId);
        return Result.success(fosters);
    }
    /**
     * Query all
     */
    @GetMapping("/selectAll")
    public Result selectAll(Foster foster ) {
        List<Foster> list = fosterService.selectAll(foster);
        return Result.success(list);
    }
    @GetMapping("/selectAllByUserId")
    public Result selectAll(int userId ) {
        List<Foster> list = fosterService.selectAllByUserId(userId);
        return Result.success(list);
    }
    @GetMapping("/selectByUserName")
    public Result selectByUserId(String petName ) {
        List<Foster> list = fosterService.selectByUserId(petName);
        return Result.success(list);
    }
    /**
     * Page query
     */
    @GetMapping("/selectPage")
    public Result selectPage(Foster foster,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Foster> page = fosterService.selectPage(foster, pageNum, pageSize);
        return Result.success(page);
    }

}
