package cn.stylefeng.guns.modular.vsshop.controller;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.vsshop.model.VssUser;
import cn.stylefeng.guns.modular.vsshop.service.IVssUserService;

import java.util.Date;

/**
 * 第三方用户控制器
 *
 * @author fengshuonan
 * @Date 2019-03-09 21:56:42
 */
@Controller
@RequestMapping("/vssUser")
public class VssUserController extends BaseController {

    private String PREFIX = "/vsshop/vssUser/";

    @Autowired
    private IVssUserService vssUserService;

    /**
     * 跳转到第三方用户首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "vssUser.html";
    }

    /**
     * 跳转到添加第三方用户
     */
    @RequestMapping("/vssUser_add")
    public String vssUserAdd() {
        return PREFIX + "vssUser_add.html";
    }

    /**
     * 跳转到修改第三方用户
     */
    @RequestMapping("/vssUser_update/{vssUserId}")
    public String vssUserUpdate(@PathVariable String vssUserId, Model model) {
        VssUser vssUser = vssUserService.selectById(vssUserId);
        model.addAttribute("item",vssUser);
        LogObjectHolder.me().set(vssUser);
        return PREFIX + "vssUser_edit.html";
    }

    /**
     * 获取第三方用户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Permission
    public Object list(String condition) {
        return vssUserService.selectList(null);
    }

    /**
     * 新增第三方用户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object add(VssUser vssUser) {
        vssUser.setUuid(RandomUtil.randomUUID().replaceAll("-",""));
        vssUser.setInsertTime(new Date());
        vssUserService.insert(vssUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除第三方用户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object delete(@RequestParam String vssUserId) {
        vssUserService.deleteById(vssUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改第三方用户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object update(VssUser vssUser) {
        vssUserService.updateById(vssUser);
        return SUCCESS_TIP;
    }

    /**
     * 第三方用户详情
     */
    @RequestMapping(value = "/detail/{vssUserId}")
    @ResponseBody
    @Permission
    public Object detail(@PathVariable("vssUserId") String vssUserId) {
        return vssUserService.selectById(vssUserId);
    }
}
