package cn.ruanyun.backInterface.modules.business.goodsPackage.service;

import cn.ruanyun.backInterface.common.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.ruanyun.backInterface.modules.business.goodsPackage.pojo.GoodsPackage;

import java.util.List;

/**
 * 商品套餐接口
 * @author fei
 */
public interface IGoodsPackageService extends IService<GoodsPackage> {


      /**
        * 插入或者更新goodsPackage
        * @param goodsPackage
       */
     void insertOrderUpdateGoodsPackage(GoodsPackage goodsPackage);



      /**
       * 移除goodsPackage
       * @param ids
       */
     void removeGoodsPackage(String ids);

    /**
     * App查询商家商品详情
     * @param ids
     * @return
     */
     Result<Object>  GetGoodsPackage(String ids);
}