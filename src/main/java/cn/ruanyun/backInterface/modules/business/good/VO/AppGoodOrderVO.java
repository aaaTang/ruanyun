package cn.ruanyun.backInterface.modules.business.good.VO;

import cn.ruanyun.backInterface.modules.business.discountMy.pojo.DiscountMy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @program: ruanyun
 * @description:
 * @author: fei
 * @create: 2020-03-27 23:22
 **/
@Data
@Accessors(chain = true)
public class AppGoodOrderVO {


    private String id;


    /**
     * 商品名称
     */
    private String goodName;


    /**
     * 商品图片
     */
    private String goodPic;


    /**
     * 尺寸
     */
    private String size;


    /**
     * 颜色
     */
    private String color;


    /**
     * 数量
     */
    private Integer count;

    /**
     * 商品新价格
     */
    private BigDecimal goodNewPrice;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 我的优惠券id
     */
    private String discountMyId;

    /**
     * 满多少
     */
    private BigDecimal fullMoney;

    /**
     * 减多少
     */
    private BigDecimal subtractMoney;


}