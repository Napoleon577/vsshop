package cn.stylefeng.guns.modular.vsshop.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.modular.supermms.MD5Util;
import cn.stylefeng.guns.modular.supermms.MmsTemplate;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class DownloadController extends BaseController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/down")
    public String down(){
        return "/down.html";
    }


}
