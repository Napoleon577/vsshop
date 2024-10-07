package cn.stylefeng.guns.modular.vsshop.controller;

import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.modular.vsshop.model.ApiResponse;
import cn.stylefeng.guns.modular.vsshop.model.VssCode;
import cn.stylefeng.guns.modular.vsshop.model.VssUser;
import cn.stylefeng.guns.modular.vsshop.service.IVssCodeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/apiDownload")
public class ApiDownloadController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ApiDownloadController.class);

    @Autowired
    IVssCodeService vssCodeService;

    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public ApiResponse getFile(@Valid VssCode vssCode, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            StringBuffer sb=new StringBuffer();
            for(ObjectError objectError : bindingResult.getAllErrors()){
                sb.append(((FieldError)objectError).getField() +" : ").append(objectError.getDefaultMessage());
            }
            return ApiResponse.fail(sb.toString());
        }

        //预处理
        vssCode.setCode(vssCode.getCode().trim());
        vssCode.setDownUrl(vssCode.getDownUrl().trim());
        vssCode.setIp(HttpContext.getIp());
        try {
            return ApiResponse.success(vssCodeService.getFile(vssCode));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ApiResponse.fail(e.getMessage());
        }
    }

}
