package com.property_management.controller;

import com.property_management.common.R;
import com.property_management.dao.House;
import com.property_management.dao.HouseDTO;
import com.property_management.enums.SuccessResultCode;
import com.property_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    //获取所有房屋信息
    @GetMapping("/getAll")
    public R<List<House>> getAll() {
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                houseService.getAll());
    }

    //获取房屋详情
    @GetMapping("/getDetail")
    public R<House> getDetail(@RequestParam("id") Long id){
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                houseService.getDetail(id));
    }

    //获取房屋详细信息，包括小区和楼栋等
    @GetMapping("/getDetailWithCommunityAndBuilding")
    public R<HouseDTO> getDetailWithCommunityAndBuilding(@RequestParam("id") Long id){
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                houseService.getDetailWithCommunityAndBuilding(id));
    }

    @GetMapping("/getAllWithCommunityAndBuilding")
    public R<List<HouseDTO>> getAllWithCommunityAndBuilding(){
        return R.success(SuccessResultCode.SUCCESS.getCode(), "获取成功",
                houseService.getAllWithCommunityAndBuilding());
    }

    //新增房屋信息
    @PostMapping("/addHouse")
    public R<Void> addHouse(@Validated @RequestBody House house){
        houseService.addHouse(house);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "添加成功");
    }

    //删除房屋信息
    @PostMapping("/deleteHouse")
    public R<Void> deleteHouse(@Validated @RequestBody House house){
        houseService.deleteHouse(house);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "删除成功");
    }

    //修改房屋信息
    @PostMapping("/updateHouse")
    public R<Void> updateHouse(@Validated @RequestBody House house){
        houseService.updateHouse(house);
        return R.success(SuccessResultCode.SUCCESS.getCode(), "修改成功");
    }
}
