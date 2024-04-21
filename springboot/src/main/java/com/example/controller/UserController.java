package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * User front interface
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * Add
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    /**
     * Delete
     */
    @DeleteMapping("/delete/{uid}")
    public Result deleteById(@PathVariable Integer uid) {
        userService.deleteById(uid);
        return Result.success();
    }

    /**
     * Batch delete
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> uids) {
        userService.deleteBatch(uids);
        return Result.success();
    }

    /**
     * Update
     */
    @PostMapping("/update")
    public Result updateById(@RequestBody User user) {
        System.out.println(user);
        userService.updateById(user);
        return Result.success();
    }

    /**
     * Query by UID
     */
    @GetMapping("/selectById/{uid}")
    public Result selectById(@PathVariable Integer uid) {
        User user = userService.selectById(uid);
        return Result.success(user);
    }

    /**
     * Query all
     */
    @GetMapping("/selectAll")
    public Result selectAll(User user) {
        List<User> list = userService.selectAll(user);
        return Result.success(list);
    }

    /**
     * Page query
     */
    @GetMapping("/selectPage")
    public Result selectPage(User user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<User> page = userService.selectPage(user, pageNum, pageSize);
        return Result.success(page);
    }

}