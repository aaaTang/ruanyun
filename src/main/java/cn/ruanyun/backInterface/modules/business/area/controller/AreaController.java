package cn.ruanyun.backInterface.modules.business.area.controller;

import cn.ruanyun.backInterface.common.utils.EmptyUtil;
import cn.ruanyun.backInterface.common.utils.ResultUtil;
import cn.ruanyun.backInterface.common.vo.Result;
import cn.ruanyun.backInterface.modules.business.area.pojo.Area;
import cn.ruanyun.backInterface.modules.business.area.service.IAreaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author fei
 * 城市管理管理接口
 */
@Slf4j
@RestController
@RequestMapping("/ruanyun/area")
@Transactional
public class AreaController {

    @Autowired
    private IAreaService iAreaService;


   /**
     * 更新或者插入数据
     * @param area
     * @return
    */
    @PostMapping(value = "/insertOrderUpdateArea")
    public Result<Object> insertOrderUpdateArea(Area area){
        try {
            iAreaService.insertOrderUpdateArea(area);
            return new ResultUtil<>().setSuccessMsg("插入或者更新成功!");
        }catch (Exception e) {
            return new ResultUtil<>().setErrorMsg(201, e.getMessage());
        }
    }

    /**
     * 移除数据
     * @param ids
     * @return
    */
    @PostMapping(value = "/removeArea")
    public Result<Object> removeArea(String ids){
        try {
            iAreaService.removeArea(ids);
            return new ResultUtil<>().setSuccessMsg("移除成功！");
        }catch (Exception e) {
            return new ResultUtil<>().setErrorMsg(201, e.getMessage());
        }
    }

    /**
     * 查询全部及模糊查询
     * @return
     */
    @PostMapping(value = "/queryArea")
    public Result<List<Area>> getAll(String title){
        List<Area> list = null;
        if(EmptyUtil.isEmpty(title)){
           list = iAreaService.list(new QueryWrapper<Area>().lambda().orderByDesc(Area::getAreaIndex));
        }else {
           list = iAreaService.list(Wrappers. <Area>lambdaQuery().like(Area::getTitle,title));
        }
        return new ResultUtil<List<Area>>().setData(list);
    }




}