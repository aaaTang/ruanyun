package cn.ruanyun.backInterface.common.enums;

import cn.ruanyun.backInterface.modules.business.goodsPackage.pojo.GoodsPackage;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 订单类型枚举
 */
public enum OrderTypeEnum {


    APPOINTMENT (1,"预约"),
    ORDER(2,"订单"),
    GOODSPACKAGE (1,"套餐订单"),
    GOOD(2,"商品订单");

    OrderTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    @EnumValue
    private final  int code;
    private  String value;

}
