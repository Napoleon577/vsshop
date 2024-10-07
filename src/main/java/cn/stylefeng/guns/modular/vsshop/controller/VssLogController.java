package cn.stylefeng.guns.modular.vsshop.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.factory.PageFactory;
import cn.stylefeng.guns.core.common.page.PageInfoBT;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.vsshop.model.VssCode;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.vsshop.model.VssLog;
import cn.stylefeng.guns.modular.vsshop.service.IVssLogService;

/**
 * 下载日志控制器
 *
 * @author fengshuonan
 * @Date 2019-03-13 22:54:27
 */
@Controller
@RequestMapping("/vssLog")
public class VssLogController extends BaseController {

    private String PREFIX = "/vsshop/vssLog/";

    @Autowired
    private IVssLogService vssLogService;

    /**
     * 跳转到下载日志首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "vssLog.html";
    }

    /**
     * 跳转到添加下载日志
     */
    @RequestMapping("/vssLog_add")
    public String vssLogAdd() {
        return PREFIX + "vssLog_add.html";
    }

    /**
     * 跳转到修改下载日志
     */
    @RequestMapping("/vssLog_update/{vssLogId}")
    public String vssLogUpdate(@PathVariable String vssLogId, Model model) {
        VssLog vssLog = vssLogService.selectById(vssLogId);
        model.addAttribute("item",vssLog);
        LogObjectHolder.me().set(vssLog);
        return PREFIX + "vssLog_edit.html";
    }

    /**
     * 获取下载日志列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Permission
    public Object list(VssLog vssLog) {
        if(StrUtil.isEmpty(vssLog.getCode())){
            vssLog.setCode(null);
        }
        Page<VssLog> page = new PageFactory<VssLog>().defaultPage();
        EntityWrapper entityWrapper=new EntityWrapper<>(vssLog);

        entityWrapper.orderBy("insert_time",false);
        page.setRecords(vssLogService.selectPage(page,entityWrapper).getRecords());
        return new PageInfoBT<>(page);
    }


    /**
     * 下载日志详情
     */
    @RequestMapping(value = "/detail/{vssLogId}")
    @ResponseBody
    @Permission
    public Object detail(@PathVariable("vssLogId") String vssLogId) {
        return vssLogService.selectById(vssLogId);
    }
}
