package cn.stylefeng.guns.modular.vsshop.service;

import cn.stylefeng.guns.modular.vsshop.model.VssCode;
import cn.stylefeng.guns.modular.vsshop.model.VssUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianyinlin
 * @since 2019-03-09
 */
public interface IVssCodeService extends IService<VssCode> {

    String getFile(VssCode vssCode) throws Exception;

    void createCode(String source, String type, Integer num);
}
