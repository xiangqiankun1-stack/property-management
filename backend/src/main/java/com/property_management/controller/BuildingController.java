package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Building;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    /**
     * 获取楼栋列表
     * GET /api/v1/buildings
     */
    @GetMapping
    public R<List<Building>> list() {
        List<Building> list = buildingService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个楼栋
     * GET /api/v1/buildings/{id}
     */
    @GetMapping("/{id}")
    public R<Building> getById(@PathVariable Long id) {
        Building building = buildingService.getById(id);
        if (building == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "楼栋不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", building);
    }

    /**
     * 新增楼栋
     * POST /api/v1/buildings
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody Building building) {
        boolean result = buildingService.add(building);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新楼栋
     * PUT /api/v1/buildings/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody Building building) {
        building.setId(id);
        boolean result = buildingService.update(building);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除楼栋
     * DELETE /api/v1/buildings/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = buildingService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
