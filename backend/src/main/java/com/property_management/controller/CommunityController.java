package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Community;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/communities")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    /**
     * 获取小区列表
     * GET /api/v1/communities
     */
    @GetMapping
    public R<List<Community>> list() {
        List<Community> list = communityService.getAll();
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个小区
     * GET /api/v1/communities/{id}
     */
    @GetMapping("/{id}")
    public R<Community> getById(@PathVariable Long id) {
        Community community = communityService.getById(id);
        if (community == null) {
            return R.fail(FailResultCode.FAIL.getCode(), "小区不存在");
        }
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", community);
    }

    /**
     * 新增小区
     * POST /api/v1/communities
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody Community community) {
        boolean result = communityService.add(community);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "新增成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "新增失败");
    }

    /**
     * 更新小区
     * PUT /api/v1/communities/{id}
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody Community community) {
        community.setId(id);
        boolean result = communityService.update(community);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "更新成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "更新失败");
    }

    /**
     * 删除小区
     * DELETE /api/v1/communities/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean result = communityService.delete(id);
        if (result) {
            return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
        }
        return R.fail(FailResultCode.FAIL.getCode(), "删除失败");
    }
}
