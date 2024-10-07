package cn.stylefeng.guns;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.modular.vsshop.common.HttpVssUserHelper;
import cn.stylefeng.guns.modular.vsshop.common.OKHttpHelper;
import cn.stylefeng.guns.modular.vsshop.core.CsdnHandleDownload;
import cn.stylefeng.guns.modular.vsshop.model.HttpVssUser;
import cn.stylefeng.guns.modular.vsshop.model.VssUser;
import cn.stylefeng.guns.modular.vsshop.service.IVssCodeService;
import cn.stylefeng.guns.modular.vsshop.service.IVssUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * <spring boot初始化>
 *
 * @version 1.0
 * @author  2018年8月5日 下午1:48:44
 */
@Component("InitSpringBoot")
public class InitSpringBoot {

    private Logger log = LoggerFactory.getLogger(InitSpringBoot.class);

    @Autowired
    IVssCodeService vssCodeService;

    @Autowired
    IVssUserService vssUserService;

    @PostConstruct
    public void run(){
        try {
            EntityWrapper entityWrapper=new EntityWrapper();
            entityWrapper.eq("login_status","1");
            entityWrapper.eq("status","1");
            List<VssUser> vssUserList=vssUserService.selectList(entityWrapper);
            if(CollUtil.isNotEmpty(vssUserList)){
                for(int i=0;i<vssUserList.size();i++){
                    OkHttpClient okHttpClient=OKHttpHelper.buildOKHttpClient().build();
                    HttpVssUser httpVssUser=new HttpVssUser(okHttpClient,vssUserList.get(i));
                    HttpVssUserHelper.httpVssUserList.add(httpVssUser);
                }
            }else {
                log.info("no valid vssuser");
            }
            log.info("###################### vssuserList init SUCCESS! ###############################");
        } catch (Exception e) {
            log.error("###################### vssuserList init ERROR! ###############################", e);
        }
    }

}
