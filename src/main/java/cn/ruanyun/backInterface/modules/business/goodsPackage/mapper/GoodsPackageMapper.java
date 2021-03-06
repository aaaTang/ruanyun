package cn.ruanyun.backInterface.modules.business.goodsPackage.mapper;

import cn.ruanyun.backInterface.modules.business.goodsPackage.DTO.ShopParticularsDTO;
import cn.ruanyun.backInterface.modules.business.goodsPackage.VO.AppGoodsPackageListVO;
import cn.ruanyun.backInterface.modules.business.goodsPackage.VO.ShopDatelistVO;
import cn.ruanyun.backInterface.modules.business.goodsPackage.VO.ShopParticularsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.ruanyun.backInterface.modules.business.goodsPackage.pojo.GoodsPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品套餐数据处理层
 * @author fei
 */
public interface GoodsPackageMapper extends BaseMapper<GoodsPackage> {


    /**
     * 修改店铺详情
     */
    void UpdateShopParticulars(@Param("obj")ShopParticularsDTO shopParticularsDTO);

    /**
     * 后端获取店铺列表
     */
    List<ShopDatelistVO> getShopDateList(String username, String shopName, Integer storeType);
}