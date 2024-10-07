package cn.stylefeng.guns.modular.vsshop.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.modular.supermms.MD5Util;
import cn.stylefeng.guns.modular.supermms.MmsTemplate;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by jianyinlin on 2019/3/19
 */
@RestController
@RequestMapping("/api")
public class SuperMmsController extends BaseController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/audit",method = RequestMethod.POST)
    @ResponseBody
    public MmsTemplate audit(@RequestBody MmsTemplate mmsTemplateQuery){
        MmsTemplate result=new MmsTemplate();
        logger.info("audit收到数据："+JSON.toJSONString(mmsTemplateQuery));
        JSONObject id=new JSONObject();
        id.put("importId",mmsTemplateQuery.getId().getString("mmsId "));
        result.setId(id);

        JSONObject content=new JSONObject();
        content.put("code","0");
        content.put("message","success");
        result.setContent(content);

        JSONObject option=new JSONObject();
        option.put("resptime",System.currentTimeMillis()+"");
        result.setOption(option);

        Map<String,Object> map= BeanUtil.beanToMap(result,false,true);
        JSONObject jsonObject=(JSONObject) JSON.toJSON(map);
        String data=JSON.toJSONString(jsonObject);
        logger.info("原始数据："+data);
        try {
            result.setSign(MD5Util.getMD5Base64(data));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("发送数据："+JSON.toJSONString(result));
        return result;
    }

    @RequestMapping(value = "/sendStatus",method = RequestMethod.POST)
    @ResponseBody
    public void sendStatus(@RequestBody MmsTemplate mmsTemplateQuery){
        logger.info("sendStatus收到数据："+JSON.toJSONString(mmsTemplateQuery));
    }
}
