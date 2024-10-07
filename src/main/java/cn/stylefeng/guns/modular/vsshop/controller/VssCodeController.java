package cn.stylefeng.guns.modular.vsshop.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.factory.PageFactory;
import cn.stylefeng.guns.core.common.page.PageInfoBT;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.system.model.LoginLog;
import cn.stylefeng.guns.modular.vsshop.common.UrlSource;
import cn.stylefeng.guns.modular.vsshop.common.VssUserType;
import cn.stylefeng.guns.modular.vsshop.model.ApiResponse;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.vsshop.model.VssCode;
import cn.stylefeng.guns.modular.vsshop.service.IVssCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 激活码控制器
 *
 * @author fengshuonan
 * @Date 2019-03-09 21:54:32
 */
@Controller
@RequestMapping("/vssCode")
public class VssCodeController extends BaseController {

    private String PREFIX = "/vsshop/vssCode/";

    @Autowired
    private IVssCodeService vssCodeService;

    /**
     * 跳转到激活码首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "vssCode.html";
    }

    /**
     * 跳转到添加激活码
     */
    @RequestMapping("/vssCode_add")
    public String vssCodeAdd() {
        return PREFIX + "vssCode_add.html";
    }

    /**
     * 跳转到修改激活码
     */
    @RequestMapping("/vssCode_update/{vssCodeId}")
    public String vssCodeUpdate(@PathVariable String vssCodeId, Model model) {
        VssCode vssCode = vssCodeService.selectById(vssCodeId);
        model.addAttribute("item",vssCode);
        LogObjectHolder.me().set(vssCode);
        return PREFIX + "vssCode_edit.html";
    }

    /**
     * 获取激活码列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Permission
    public Object list(VssCode vssCode) {
        if(!ShiroKit.getUser().getRoleNames().contains("超级管理员")){
            vssCode.setOwner(ShiroKit.getUser().getName());
        }
        if(StrUtil.isEmpty(vssCode.getCode())){
            vssCode.setCode(null);
        }
        Page<VssCode> page = new PageFactory<VssCode>().defaultPage();
        EntityWrapper entityWrapper=new EntityWrapper<>(vssCode);
        entityWrapper.orderBy("insert_time",false);
        page.setRecords(vssCodeService.selectPage(page,entityWrapper).getRecords());
        return new PageInfoBT<>(page);
    }

    /**
     * 新增激活码
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Permission
    public ApiResponse add(@RequestParam String source,@RequestParam String type,@RequestParam Integer num) {
        if(StrUtil.isEmpty(source) || StrUtil.isEmpty(type) || num==null){
            return ApiResponse.fail("来源、类型、数量不能为空");
        }

        if(num>10000){
            return ApiResponse.fail("一次最多生成10000");
        }
        vssCodeService.createCode(source,type,num);
        return ApiResponse.success("生成成功！");
    }

    /**
     * 删除激活码
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission
    public Object delete(@RequestParam String vssCodeId) {
        vssCodeService.deleteById(vssCodeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改激活码
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Permission
    public Object update(VssCode vssCode) {
        vssCodeService.updateById(vssCode);
        return SUCCESS_TIP;
    }

    /**
     * 激活码详情
     */
    @RequestMapping(value = "/detail/{vssCodeId}")
    @ResponseBody
    @Permission
    public Object detail(@PathVariable("vssCodeId") String vssCodeId) {
        return vssCodeService.selectById(vssCodeId);
    }

    /**
     * 下载激活码
     */
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public void detailListExport(VssCode vssCodeQuery,ModelMap map, HttpServletRequest request, HttpServletResponse response)  {
        ExportParams params = new ExportParams("激活码", "1", ExcelType.XSSF);
        params.setFreezeCol(2);
        try {
            if(!ShiroKit.getUser().getRoleNames().contains("超级管理员")){
                vssCodeQuery.setOwner(ShiroKit.getUser().getName());
            }
            if(StrUtil.isEmpty(vssCodeQuery.getCode())){
                vssCodeQuery.setCode(null);
            }
            EntityWrapper entityWrapper=new EntityWrapper<>(vssCodeQuery);
            entityWrapper.orderBy("insert_time",false);
            List<VssCode> vssCodeListTemp=vssCodeService.selectList(entityWrapper);
            vssCodeListTemp.forEach(vssCode -> {
                String type=vssCode.getType();
                if(String.valueOf(VssUserType.NORMAL.ordinal()).equals(type)){
                    vssCode.setType("通用");
                }else if(String.valueOf(VssUserType.VIP.ordinal()).equals(type)){
                    vssCode.setType("大积分");
                }
                String source=vssCode.getSource();
                if(String.valueOf(UrlSource.CSDN.ordinal()).equals(source)){
                    vssCode.setSource("CSDN");
                }else{
                    vssCode.setSource("未知");
                }
                String status=vssCode.getStatus();
                if("1".equals(status)){
                    vssCode.setStatus("正常");
                }else if("2".equals(status)){
                    vssCode.setStatus("已使用");
                }else if("3".equals(status)){
                    vssCode.setStatus("废弃");
                }
                vssCode.setCode(String.format("http://www.sheepsun.com/down?code=%s", vssCode.getCode()));
            });
            map.put(NormalExcelConstants.DATA_LIST, vssCodeListTemp); // 数据集合
            map.put(NormalExcelConstants.CLASS, VssCode.class);//导出实体
            map.put(NormalExcelConstants.PARAMS, params);//参数
            map.put(NormalExcelConstants.FILE_NAME, String.format("%s个-%s",vssCodeListTemp.size(), DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN)));//文件名称
        } catch (Exception e) {
            ApiResponse.fail("下载激活码失败！"+e.getMessage());
        }

        PoiBaseView.render(map,request,response,NormalExcelConstants.EASYPOI_EXCEL_VIEW); ;//View名称
    }
}
