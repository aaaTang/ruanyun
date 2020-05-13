package cn.ruanyun.backInterface.modules.business.profitPercent.serviceimpl;

import cn.ruanyun.backInterface.common.enums.ProfitTypeEnum;
import cn.ruanyun.backInterface.modules.business.profitPercent.mapper.ProfitPercentMapper;
import cn.ruanyun.backInterface.modules.business.profitPercent.pojo.ProfitPercent;
import cn.ruanyun.backInterface.modules.business.profitPercent.service.IProfitPercentService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import cn.ruanyun.backInterface.common.utils.ToolUtil;
import cn.ruanyun.backInterface.common.utils.SecurityUtil;
import cn.ruanyun.backInterface.common.utils.ThreadPoolUtil;


/**
 * 分红比例接口实现
 * @author z
 */
@Slf4j
@Service
@Transactional
public class IProfitPercentServiceImpl extends ServiceImpl<ProfitPercentMapper, ProfitPercent> implements IProfitPercentService {


       @Autowired
       private SecurityUtil securityUtil;

       @Override
       public void insertOrderUpdateProfitPercent(ProfitPercent profitPercent) {

           if (ToolUtil.isEmpty(profitPercent.getCreateBy())) {

                       profitPercent.setCreateBy(securityUtil.getCurrUser().getId());
                   }else {

                       profitPercent.setUpdateBy(securityUtil.getCurrUser().getId());
                   }


                   Mono.fromCompletionStage(CompletableFuture.runAsync(() -> this.saveOrUpdate(profitPercent)))
                           .publishOn(Schedulers.fromExecutor(ThreadPoolUtil.getPool()))
                           .toFuture().join();
       }

      @Override
      public void removeProfitPercent(String ids) {

          CompletableFuture.runAsync(() -> this.removeByIds(ToolUtil.splitterStr(ids)));
      }

      @Override
      public ProfitPercent getProfitPercentLimitOne(ProfitTypeEnum profitTypeEnum) {

           return Optional.ofNullable(this.getOne(Wrappers.<ProfitPercent>lambdaQuery()
           .eq(ProfitPercent::getProfitTypeEnum, profitTypeEnum)
           .orderByDesc(ProfitPercent::getCreateTime)
           .last("limit 1"))).orElse(null);
      }
}