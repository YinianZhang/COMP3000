package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Pet;
import com.example.entity.Pet;
import com.example.exception.CustomException;
import com.example.mapper.PetMapper;
import com.example.mapper.PetMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Pet service layer
 **/
@Service
public class PetService {

    @Resource
    private PetMapper petMapper;

    /**
     * Add a pet
     */
    public void add(Pet pet) {
        Pet dbPet = petMapper.selectByName(pet.getName());
        if (ObjectUtil.isNotNull(dbPet)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(pet.getPassword())) {
            pet.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(pet.getName())) {
            pet.setName(pet.getName());
        }
        pet.setRole(RoleEnum.USER.name());
        petMapper.insert(pet);
    }

    /**
     * Delete a pet by PID
     */
    public void deleteById(Integer pid) {
        petMapper.deleteById(pid);
    }

    /**
     * Batch delete pets
     */
    public void deleteBatch(List<Integer> pids) {
        for (Integer pid : pids) {
            petMapper.deleteById(pid);
        }
    }

    /**
     * Update a pet by ID
     */
    public void updateById(Pet pet) {
        petMapper.updateById(pet);
    }

    /**
     * Select a pet by PID
     */
    public Pet selectById(Integer pid) {
        return petMapper.selectById(pid);
    }

    /**
     * Select all pets
     */
    public List<Pet> selectAll(Pet pet) {
        return petMapper.selectAll(pet);
    }

    /**
     * Page query pets
     */
    public PageInfo<Pet> selectPage(Pet pet, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Pet> list = petMapper.selectAll(pet);
        return PageInfo.of(list);
    }
}