package cn.ruanyun.backInterface.modules.base.serviceimpl;


import cn.ruanyun.backInterface.modules.base.mapper.SettingDao;
import cn.ruanyun.backInterface.modules.base.pojo.Setting;
import cn.ruanyun.backInterface.modules.base.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配置接口实现
 * @author fei
 */
@Slf4j
@Service
@Transactional
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingDao settingDao;

    @Override
    public Setting get(String id) {

        return settingDao.findById(id).orElse(new Setting(id));
    }

    @Override
    public Setting saveOrUpdate(Setting setting) {

        return settingDao.saveAndFlush(setting);
    }
}