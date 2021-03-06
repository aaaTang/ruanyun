package cn.ruanyun.backInterface.modules.base.serviceimpl;


import cn.ruanyun.backInterface.modules.base.mapper.DictDao;
import cn.ruanyun.backInterface.modules.base.pojo.Dict;
import cn.ruanyun.backInterface.modules.base.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典接口实现
 * @author fei
 */
@Slf4j
@Service
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public DictDao getRepository() {
        return dictDao;
    }

    @Override
    public List<Dict> findAllOrderBySortOrder() {

        return dictDao.findAllOrderBySortOrder();
    }

    @Override
    public Dict findByType(String type) {

        return dictDao.findByType(type);
    }

    @Override
    public List<Dict> findByTitleOrTypeLike(String key) {

        return dictDao.findByTitleOrTypeLike(key);
    }
}