package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.House;
import com.property_management.dao.HouseDTO;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.mapper.HouseMapper;
import com.property_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    /**
     * 获取房屋列表
     * GET /api/v1/houses
     */
    @GetMapping
    public R<List<House>> list() {
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                houseService.getAll());
    }

    /**
     * 获取单个房屋
     * GET /api/v1/houses/{id}
     */
    @GetMapping("/{id}")
    public R<House> getById(@PathVariable Long id) {
        House house = houseService.getById(id);
        if (house == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "房屋不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", house);
    }

    /**
     * 获取房屋详细信息，包括小区和楼栋等
     * GET /api/v1/houses/{id}/detail
     */
    @GetMapping("/{id}/detail")
    public R<HouseDTO> getDetailWithCommunityAndBuilding(@PathVariable Long id) {
        HouseDTO houseDTO = houseService.getDetailWithCommunityAndBuilding(id);
        if (houseDTO == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "房屋不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", houseDTO);
    }

    /**
     * 获取所有房屋详细信息，包括小区和楼栋等
     * GET /api/v1/houses/detail
     */
    @GetMapping("/detail")
    public R<List<HouseDTO>> getAllWithCommunityAndBuilding() {
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                houseService.getAllWithCommunityAndBuilding());
    }

    /**
     * 新增房屋
     * POST /api/v1/houses
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody House house) {
        if (houseService.add(house)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新房屋
     * PUT /api/v1/houses/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody House house) {
        house.setId(id);
        if (houseService.update(house)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除房屋
     * DELETE /api/v1/houses/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        if (houseService.delete(id)) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
