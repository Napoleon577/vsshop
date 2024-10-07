package cn.stylefeng.guns.modular.supermms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianyinlin on 2019/3/18
 */
public class SignUtil {
    /**
     * 通用加密发送文本
     * @param mmsTemplate
     * @return
     */
    public static String getSigned(MmsTemplate mmsTemplate) throws Exception {
        Map<String,Object> map= BeanUtil.beanToMap(mmsTemplate,false,true);
        JSONObject jsonObject=(JSONObject) JSON.toJSON(map);
        mmsTemplate.setSign(MD5Util.getMD5Base64((JSON.toJSONString(jsonObject))));
        Map<String,Object> mapResult=BeanUtil.beanToMap(mmsTemplate,false,false);
        return JSON.toJSONString(mapResult);
    }

}
