package com.example.controller;

import com.example.common.Result;
import com.example.entity.Pet;
import com.example.service.PetService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Pet front interface
 **/
@RestController
@RequestMapping("/pet")
public class PetController {

    @Resource
    private PetService petService;

    /**
     * Add
     */
    @PostMapping("/add")
    public Result add(@RequestBody Pet pet) {
        petService.add(pet);
        return Result.success();
    }

    /**
     * Delete
     */
    @DeleteMapping("/delete/{pid}")
    public Result deleteById(@PathVariable Integer pid) {
        petService.deleteById(pid);
        return Result.success();
    }

    /**
     * Batch delete
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> pids) {
        petService.deleteBatch(pids);
        return Result.success();
    }

    /**
     * Update
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Pet pet) {
        petService.updateById(pet);
        return Result.success();
    }

    /**
     * Query by PID
     */
    @GetMapping("/selectById/{pid}")
    public Result selectById(@PathVariable Integer pid) {
        Pet pet = petService.selectById(pid);
        return Result.success(pet);
    }

    /**
     * Query all
     */
    @GetMapping("/selectAll")
    public Result selectAll(Pet pet ) {
        List<Pet> list = petService.selectAll(pet);
        return Result.success(list);
    }

    /**
     * Page query
     */
    @GetMapping("/selectPage")
    public Result selectPage(Pet pet,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Pet> page = petService.selectPage(pet, pageNum, pageSize);
        return Result.success(page);
    }

}