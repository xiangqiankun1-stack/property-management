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
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    //获取业主列表
    @GetMapping("/getOwners")
    public R<List<Owner>> getOwners(){
        List<Owner> list = ownerService.getAll();
        if (list == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", list);
    }

    //通过id获取业主信息
    @GetMapping("/getOwnerById")
    public R<Owner> getOwnerById(@RequestParam("id") Long id){
        Owner owner = ownerService.getById(id);
        if (owner == null)
            return R.fail(FailResultCode.FAIL.getCode(),"获取失败");
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功", owner);
    }

    //添加业主
    @PostMapping("/addOwner")
    public R<Void> addOwner(@Validated @RequestBody Owner owner){
        if (owner == null)
            return R.fail(FailResultCode.FAIL.getCode(),"添加失败");
        //添加
        owner.setStatus(1);
        ownerService.addOwner(owner);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "添加成功");
    }

    //删除业主
    @PostMapping("/deleteOwner")
    public R<Void> deleteOwner(@RequestParam Long id){
        ownerService.deleteOwner(id);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
    }

    //修改业主信息
    @PostMapping("/updateOwner")
    public R<Void> updateOwner(@Validated @RequestBody Owner owner){
        ownerService.updateOwner(owner);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
    }

}
