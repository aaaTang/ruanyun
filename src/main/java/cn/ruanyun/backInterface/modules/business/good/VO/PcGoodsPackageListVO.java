package cn.ruanyun.backInterface.modules.business.good.VO;

import cn.ruanyun.backInterface.common.enums.GoodTypeEnum;
import cn.ruanyun.backInterface.modules.business.goodsIntroduce.VO.GoodsIntroduceVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class PcGoodsPackageListVO {

    private  String id;
    private GoodTypeEnum typeEnum;

    /**
     * 分类id
     */
    private String goodCategoryId;

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
     * 积分
     */
    private Integer integral;

    /***
     * 分类名称
     */
    private String goodCategoryName;

    /**
     * 商品介绍
     */
    private List productsIntroduction;

    /**
     * 购买须知
     */
    private List purchaseNotes;
    /**
     * 商品是否删除
     */
    private Integer delFlag;
    /***
     * 商家名称
     */
    private String shopName;

    /**
     * 商家状态 默认0正常 -1拉黑
     */
    private Integer status;
}
