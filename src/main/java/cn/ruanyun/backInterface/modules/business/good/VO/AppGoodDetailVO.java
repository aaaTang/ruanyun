package cn.ruanyun.backInterface.modules.business.good.VO;

import cn.ruanyun.backInterface.modules.business.discountCoupon.VO.DiscountCouponListVO;

import cn.ruanyun.backInterface.modules.business.goodService.GoodServerVO;
import cn.ruanyun.backInterface.modules.business.goodService.pojo.GoodService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: ruanyun
 * @description:
 * @author: fei
 * @create: 2020-03-27 22:39
 **/

@Data
@Accessors(chain = true)
public class  AppGoodDetailVO {


    private String id;

    /**
     * 商品名称
     */
    private String goodName;


    /**
     * 商品图片
     */
    private String goodPics;


    /**
     * 商品旧价格
     */
    private BigDecimal goodOldPrice;


    /**
     * 商品新价格
     */
    private BigDecimal goodNewPrice;


    /**
     * 商品详情
     */
    private String goodDetails;

    /**
     * 店铺数据
     */
    private AppShopVO shopList;
    /**
     * 是否收藏  0否 1 是
     */
    private Integer favorite;

    /**
     * 优惠券
     */
    private List<DiscountCouponListVO> discountCouponListVOS;

    /**
     * 商品服务
     */
    private List<GoodServerVO> goodsService;

    /**
     * 心愿单
     */
    private String wishList;
}
