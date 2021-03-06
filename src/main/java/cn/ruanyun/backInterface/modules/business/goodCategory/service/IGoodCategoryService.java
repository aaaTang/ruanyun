package cn.ruanyun.backInterface.modules.business.goodCategory.service;


import cn.ruanyun.backInterface.modules.business.goodCategory.VO.GoodCategoryListVO;
import cn.ruanyun.backInterface.modules.business.goodCategory.VO.GoodCategoryVO;
import cn.ruanyun.backInterface.modules.business.goodCategory.entity.GoodCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 商品分类接口
 * @author fei
 */
public interface IGoodCategoryService extends IService<GoodCategory> {

    /**
     * 插入分类
     * @param goodCategory
     */
    void  insertGoodCategory(GoodCategory goodCategory);

    /**
     * 删除分类
     * @param id
     */
    void  deleteGoodCategory(String id);

    /**
     * 修改
     * @param goodCategory
     */
    void  updateGoodCategory(GoodCategory goodCategory);

    /**
     * 获取分类列表
     * @param pid
     * @return
     */
    List<GoodCategoryVO> getGoodCategoryList(String pid);

    /**
     * 获取app分类列表
     * @return
     */
    List<GoodCategoryListVO> getAppGoodCategoryList();


    /**
     * 通过父类id获取其子类id集合
     * @param pid
     * @return
     */
    Set<String> getIdByParentId(String pid);

    /**
     * 获取分类名称
     * @param id
     * @return
     */
    String getGoodCategoryName(String id);

    /**
     * 判断分类是否有父类
     * @param id
     * @return
     */
    Boolean judgeGoodCategoryHaveParent(String id);

}