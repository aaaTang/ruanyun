package cn.ruanyun.backInterface.modules.business.good.VO;

import cn.ruanyun.backInterface.common.enums.GoodTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppGoodAndPackageListVO {

    /**
     * 商品id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品名称
     */
    private String goodId;

    /**
     * 商品类型
     */
    private GoodTypeEnum goodTypeEnum;

    /**
     * 商品名称
     */
    private String goodName;


    /**
     * 商品图片
     */
    private String goodPic;


    /**
     * 商品新价格
     */
    private BigDecimal goodNewPrice;


    /**
     * 用户名
     */
    private String nickName;


    /**
     * 用户头像
     */
    private String avatar;


    /**
     * 评分
     */
    private Double grade;


    /**
     * 评论数
     */
    private Integer commentNum;


    /**
     * 销量
     */
    private Integer SaleVolume = 0;

}
