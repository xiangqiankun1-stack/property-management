package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.Owner;
import com.property_management.enums.FailResultCode;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    /**
     * 获取业主列表
     * GET /api/v1/owners
     */
    @GetMapping()
    public R<List<Owner>> getOwners(){
        List<Owner> list = ownerService.getAll();
        if (list == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    /**
     * 获取单个业主
     * GET /api/v1/owners/{id}
     */
    @GetMapping("/{id}")
    public R<Owner> getOwnerById(@PathVariable Long id){
        Owner owner = ownerService.getById(id);
        if (owner == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", owner);
    }

    /**
     * 添加业主
     * POST /api/v1/owners
     */
    @PostMapping()
    public R<Void> addOwner(@Validated @RequestBody Owner owner){
        if (owner == null)
            return R.fail(FailResultCode.FAIL.getCode(),"添加失败");
        //添加
        owner.setStatus(1);
        ownerService.addOwner(owner);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "添加成功");
    }

    /**
     * 删除业主
     * DELETE /api/v1/owners/{id}
     */
    @DeleteMapping("/{id}")
    public R<Void> deleteOwner(@PathVariable Long id){
        ownerService.deleteOwner(id);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 修改业主信息
     * PUT /api/v1/owners/{id}
     */
    @PutMapping("/{id}")
    public R<Void> updateOwner(@PathVariable Long id, @Validated @RequestBody Owner owner){
        owner.setId(id);
        ownerService.updateOwner(owner);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
    }

}
