package cn.ruanyun.backInterface.modules.business.good.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppOneClassGoodListVO {

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
     * 商品新价格
     */
    private BigDecimal goodNewPrice;


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
