package cn.ruanyun.backInterface.modules.business.itemAttrVal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.ruanyun.backInterface.modules.business.itemAttrVal.pojo.ItemAttrVal;

import java.util.List;

/**
 * 规格属性管理接口
 * @author z
 */
public interface IItemAttrValService extends IService<ItemAttrVal> {


    /**
     * 插入或者更新itemAttrVal
     *
     * @param itemAttrVal
     */
    void insertOrderUpdateItemAttrVal(ItemAttrVal itemAttrVal);


    /**
     * 移除itemAttrVal
     *
     * @param ids
     */
    void removeItemAttrVal(String ids);

    /**
     * 通过属性值，获取属性的名字
     *
     * @param ids
     * @return
     */
    List<String> getItemAttrVals(String ids);


    /**
     * 获取规格属性值列表
     * @return
     */
    List getItemAttrValList(String keyId);
}