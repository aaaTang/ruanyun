package cn.ruanyun.backInterface.modules.business.discountMy.pojo;

import cn.ruanyun.backInterface.base.RuanyunBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 我领取的优惠券
 * @author wj
 */
@Data
@Entity
@Table(name = "t_discount_my")
@TableName("t_discount_my")
public class DiscountMy extends RuanyunBaseEntity {

    private static final long serialVersionUID = 1L;

      /**
     * 优惠券id
     */
    private String discountCouponId;
    //0未使用，1已使用，2过期
    private Integer status;

}