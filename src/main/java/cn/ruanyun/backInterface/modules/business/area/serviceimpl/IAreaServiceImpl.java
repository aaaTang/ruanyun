package cn.ruanyun.backInterface.modules.business.area.serviceimpl;

import cn.ruanyun.backInterface.common.constant.CommonConstant;
import cn.ruanyun.backInterface.modules.business.area.VO.AppAreaListVO;
import cn.ruanyun.backInterface.modules.business.area.VO.AppAreaVO;
import cn.ruanyun.backInterface.modules.business.area.VO.BackAreaVO;
import cn.ruanyun.backInterface.modules.business.area.mapper.AreaMapper;
import cn.ruanyun.backInterface.modules.business.area.pojo.Area;
import cn.ruanyun.backInterface.modules.business.area.service.IAreaService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import cn.ruanyun.backInterface.common.utils.ToolUtil;
import cn.ruanyun.backInterface.common.utils.SecurityUtil;
import cn.ruanyun.backInterface.common.utils.ThreadPoolUtil;


/**
 * 城市管理接口实现
 *
 * @author fei
 */
@Slf4j
@Service
@Transactional
public class IAreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {


    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public void insertOrderUpdateArea(Area area) {

        if (ToolUtil.isEmpty(area.getCreateBy())) {
            area.setCreateBy(securityUtil.getCurrUser().getId());
        } else {
            area.setUpdateBy(securityUtil.getCurrUser().getId());
        }
        Mono.fromCompletionStage(CompletableFuture.runAsync(() -> {

            if (ToolUtil.isEmpty(area.getParentId())) {

                area.setParentId(CommonConstant.PARENT_ID);
            }

            super.saveOrUpdate(area);

        })).publishOn(Schedulers.fromExecutor(ThreadPoolUtil.getPool())).toFuture().join();
    }

    @Override
    public void removeArea(String ids) {

        CompletableFuture.runAsync(() -> this.removeByIds(ToolUtil.splitterStr(ids)));
    }

    @Override
    public List<BackAreaVO> getBackAreaList(String pid) {

        //1.获取一级分类
        CompletableFuture<Optional<String>> parentId = CompletableFuture.supplyAsync(() -> Optional.ofNullable(pid)
                , ThreadPoolUtil.getPool());

        //2.获取封装之前的数据
        CompletableFuture<Optional<List<Area>>> getAreas = parentId.thenApplyAsync(optionalS -> optionalS
                .map(optional -> Optional.ofNullable(getAreaList(optional))).orElseGet(() ->
                        Optional.ofNullable(getAreaList(CommonConstant.PARENT_ID))),ThreadPoolUtil.getPool());

        //3.获取封装之后的数据
        CompletableFuture<List<BackAreaVO>> backAreaList = getAreas.thenApplyAsync(areas ->
                areas.map(areaList -> areaList.parallelStream().flatMap(area -> {

                    BackAreaVO backAreaVO = new BackAreaVO();
                    ToolUtil.copyProperties(area,backAreaVO);
                    return Stream.of(backAreaVO);
                }).collect(Collectors.toList())).orElse(null),ThreadPoolUtil.getPool());

        return backAreaList.join();
    }



    /**
     * 获取基础分类列表
     * @param pid
     * @return
     */
    public List<Area> getAreaList(String pid) {

        return ToolUtil.setListToNul(this.list(Wrappers.<Area>lambdaQuery()
                .eq(Area::getParentId,pid)
                .orderByDesc(Area::getCreateTime)));
    }



    @Override
    public List<AppAreaListVO> getAppAreaList() {

      return Mono.fromCompletionStage(CompletableFuture.supplyAsync(() -> Optional.ofNullable(ToolUtil.setListToNul(super
                .list(Wrappers.<Area>lambdaQuery()
                .orderByDesc(Area::getAreaIndex)))))

        .thenApplyAsync(areas -> areas.map(areaList -> areaList.parallelStream().collect(Collectors.groupingBy(Area::getAreaIndex))))
        .thenApplyAsync(areaIndexEnumListMap -> areaIndexEnumListMap.map(areaIndexEnumList -> {

            List<AppAreaListVO> appAreaListVOS = Lists.newArrayList();

            areaIndexEnumList.forEach((k ,v) -> {

                AppAreaListVO appAreaListVO = new AppAreaListVO();
                appAreaListVO.setAreaIndex(k)
                        .setAreaListVOS(v.parallelStream().flatMap(area -> {

                            AppAreaVO appAreaVO = new AppAreaVO();
                            ToolUtil.copyProperties(area, appAreaVO);
                            return Stream.of(appAreaVO);
                        }).collect(Collectors.toList()));

                appAreaListVOS.add(appAreaListVO);
            });

            return appAreaListVOS;
        }).orElse(null))).toFuture().join();
    }

    @Override
    public List<AppAreaVO> getAppHotAreaList() {

        // TODO: 2020/3/13 热门未写
        return null;
    }


}