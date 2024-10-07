package cn.stylefeng.guns.modular.vsshop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.vsshop.common.*;
import cn.stylefeng.guns.modular.vsshop.core.CsdnHandleDownload;
import cn.stylefeng.guns.modular.vsshop.core.HandleDownload;
import cn.stylefeng.guns.modular.vsshop.dao.VssLogMapper;
import cn.stylefeng.guns.modular.vsshop.dao.VssUserMapper;
import cn.stylefeng.guns.modular.vsshop.model.*;
import cn.stylefeng.guns.modular.vsshop.dao.VssCodeMapper;
import cn.stylefeng.guns.modular.vsshop.service.IVssCodeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.swagger.models.properties.UUIDProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.spi.http.HttpHandler;
import java.beans.Beans;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianyinlin
 * @since 2019-03-09
 */
@Service
public class VssCodeServiceImpl extends ServiceImpl<VssCodeMapper, VssCode> implements IVssCodeService {

    @Autowired
    VssCodeMapper vssCodeMapper;

    @Autowired
    VssLogMapper vssLogMapper;

    @Autowired
    VssUserMapper vssUserMapper;

    HandleDownload handleDownload;
    @Override
    public String getFile(VssCode vssCodeReq) throws Exception {
        EntityWrapper<VssCode> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("code",vssCodeReq.getCode());

        VssCode vssCode=selectOne(entityWrapper);
        if(vssCode!=null){

            //重复下载
            if(StrUtil.isNotEmpty(vssCode.getFilePath()) && vssCodeReq.getDownUrl().equals(vssCode.getDownUrl())){
                return vssCode.getFilePath();
            }

            //检验code
            checkVssCodeStatus(vssCode);

            EntityWrapper<VssCode> tempEntityWrapper=new EntityWrapper<>();
            tempEntityWrapper.eq("down_url",vssCodeReq.getDownUrl());

            //如果已经有下载过，则直接使用
            VssCode vssCodeTemp=selectOne(tempEntityWrapper);
            if(vssCodeTemp!=null && StrUtil.isNotEmpty(vssCodeTemp.getFilePath())){

                //请求属性赋值
                CommonUtils.copyProperties(vssCodeReq,vssCode);
                vssCode.setFilePath(vssCodeTemp.getFilePath());
                vssCode.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                vssCode.setSource(vssCode.getSource());
                vssCode.setUsedTime(new Timestamp(System.currentTimeMillis()));
                vssCode.setStatus(String.valueOf(VssCodeStatus.USED.ordinal()));
                updateById(vssCode);

                //记录日志
                //记录下载日志
                VssLog vssLog=new VssLog();
                vssLog.setUuid(RandomUtil.randomUUID().replaceAll("-",""));
                vssLog.setCode(vssCode.getCode());
                vssLog.setDownUrl(vssCode.getDownUrl());
                vssLog.setFilePath(vssCode.getFilePath());
                vssLog.setInsertTime(new Date());
                vssLog.setReuse("是");
                vssLog.setVssuser("系统");
                vssLog.setSource(vssCode.getSource());
                String coin=NumberUtil.isNumber(vssCode.getCode())?vssCode.getCoin():"0";
                vssLog.setCoin(Integer.parseInt(coin));
                vssLogMapper.insert(vssLog);
                return vssCodeTemp.getFilePath();
            }else{

                //请求属性赋值
                CommonUtils.copyProperties(vssCodeReq,vssCode);

                //获取文件地址
                vssCode.setFilePath(downFile(vssCode));

                vssCode.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                vssCode.setUsedTime(new Timestamp(System.currentTimeMillis()));
                vssCode.setStatus(String.valueOf(VssCodeStatus.USED.ordinal()));
                updateById(vssCode);

                return vssCode.getFilePath();
            }
        }else{
            throw new Exception("不存在该激活码！");
        }
    }

    @Override
    public void createCode(String source, String type, Integer num) {
        List<VssCode> vssCodeList=new ArrayList<>();
        for(int i=0;i<num;i++){
            VssCode vssCodeTemp=new VssCode();
            vssCodeTemp.setUuid(RandomUtil.randomUUID().replaceAll("-",""));
            vssCodeTemp.setCode(RandomUtil.randomString(32));
            vssCodeTemp.setInsertTime(new Timestamp(System.currentTimeMillis()));
            vssCodeTemp.setStatus(String.valueOf(VssCodeStatus.NORMAL.ordinal()));
            vssCodeTemp.setSource(source);
            vssCodeTemp.setType(type);
            vssCodeTemp.setOwner(ShiroKit.getUser().getName());
            vssCodeList.add(vssCodeTemp);
        }
        insertBatch(vssCodeList);
    }

    private void checkVssCodeStatus(VssCode vssCode) throws Exception {
        int status=NumberUtil.isNumber(vssCode.getStatus())?Integer.parseInt(vssCode.getStatus()):0;
        if(status==VssCodeStatus.NORMAL.ordinal()){
            return ;
        }
        if(status==VssCodeStatus.USED.ordinal()){
            throw new Exception("激活码已经使用！");
        }else if(status==VssCodeStatus.INVALID.ordinal()){
            throw new Exception("激活码不可用状态！");
        }else {
            throw new Exception("激活码未知状态！");
        }
    }

    private String downFile(VssCode vssCode) throws Exception {
        //获取来源
        UrlSource urlSource=CommonUtils.getSource(vssCode.getDownUrl());

        //设置下载处理类
        setHandleDownload(urlSource);

        //获取下载信息
        DownloadInfo downloadInfo=handleDownload.getDownloadInfo(vssCode.getDownUrl());

        //判断积分是否足够
        String coin=downloadInfo.getCoin();

        if(!NumberUtil.isNumber(coin)){
            throw new Exception("网络不稳定，请重试");
        }else if(Integer.parseInt(coin)>6 && String.valueOf(VssUserType.NORMAL.ordinal()).equals(vssCode.getType())){
            throw new Exception("该激活码下载最多6积分资源，请联系店家");
        }

        //获取实际下载地址
        String actualDownUrl=getActualDownUrl(vssCode,urlSource);

        vssCode.setSource(String.valueOf(urlSource.ordinal()));

        //获取下载用户
        HttpVssUser httpVssUser=null;
        if(String.valueOf(VssUserType.NORMAL.ordinal()).equals(vssCode.getType())){
            httpVssUser= HttpVssUserHelper.getHttpVssUser(urlSource,vssCode.getType(),Integer.parseInt(coin));
            if(httpVssUser==null){
                Email email=new Email();
                email.setContent(String.format("%s通用账号积分不足或者无可用通用账号！！", urlSource.name()));
                email.setTitle("自动发货警告");
                EmailHelper.send(email);
                throw new Exception("CSDN账号积分不足，联系店家");
            }
        }else if(String.valueOf(VssUserType.VIP.ordinal()).equals(vssCode.getType())){
            httpVssUser= HttpVssUserHelper.getHttpVssUser(urlSource,vssCode.getType());
            if(httpVssUser==null){
                Email email=new Email();
                email.setContent(String.format("%s无可用vip账号",urlSource.name()));
                email.setTitle("自动发货警告");
                EmailHelper.send(email);
                throw new Exception("CSDN账号积分不足，联系店家");
            }
        }
        //下载
        String filePath= handleDownload.download(httpVssUser,actualDownUrl);
        if(StrUtil.isEmpty(filePath) || filePath.endsWith("null")){
            throw new Exception("下载失败");
        }else{
            //记录下载日志
            VssLog vssLog=new VssLog();
            vssLog.setUuid(RandomUtil.randomUUID().replaceAll("-",""));
            vssLog.setCode(vssCode.getCode());
            vssLog.setDownUrl(vssCode.getDownUrl());
            vssLog.setFilePath(filePath);
            vssLog.setInsertTime(new Date());
            vssLog.setReuse("否");
            vssLog.setVssuser(httpVssUser.getVssUser().getUsername());
            vssLog.setSource(urlSource.name());
            vssLog.setCoin(Integer.parseInt(coin));
            vssLogMapper.insert(vssLog);

            //更新用户积分
            if(String.valueOf(VssUserType.NORMAL.ordinal()).equals(vssCode.getType())){
                VssUser vssUser=httpVssUser.getVssUser();
                vssUser.setCoin(NumberUtil.toStr(Integer.parseInt(vssUser.getCoin())-Integer.parseInt(coin),"0"));
                vssUser.setLastLoginTime(new Date());
                vssUserMapper.updateById(vssUser);
            }
            return filePath;
        }
    }

    private String getActualDownUrl(VssCode vssCode, UrlSource urlSource) throws Exception {
        if(urlSource.equals(UrlSource.CSDN)){
            if(!vssCode.getDownUrl().matches("https://download.csdn.net/download/([\\s\\S]*?)/([\\s\\S]*?)$")){
                throw new Exception("请输入正确下载地址！如https://download.csdn.net/download/xx/xx");
            }
            Pattern pattern=Pattern.compile("https://download.csdn.net/download/([\\s\\S]*?)/([\\s\\S]*?)$");

            Matcher matcher=pattern.matcher(vssCode.getDownUrl());
            String uploadUser="";
            String resourceId="";
            if(matcher.find()){
                uploadUser=matcher.group(1);
                resourceId=matcher.group(2);
            }

           return String.format("https://download.csdn.net/index.php/mobile/source/do_download/%s/%s", resourceId,uploadUser);
        }else {
            throw new Exception("请输入正确下载地址！");
        }
    }


    public HandleDownload setHandleDownload(UrlSource urlSource) throws Exception {
        if(urlSource.equals(UrlSource.CSDN)){
            handleDownload=(HandleDownload) SpringUtil.getBean(CsdnHandleDownload.class);
        }else if(urlSource.equals(UrlSource.UNKNOW)){
            throw new Exception("暂不支持该网站下载！");
        }

        return null;
    }
}
