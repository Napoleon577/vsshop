package cn.stylefeng.guns.modular.supermms;

import com.alibaba.fastjson.JSONObject;

public class MmsTemplate {
    private JSONObject id;//	是	String	需要携带产品ID
    private JSONObject content;

    private JSONObject option;//	reqtime:	是	String	请求时间戳
    private String sign;//		是	Md5+base64	S校验和

    public JSONObject getId() {
        return id;
    }

    public void setId(JSONObject id) {
        this.id = id;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }

    public JSONObject getOption() {
        return option;
    }

    public void setOption(JSONObject option) {
        this.option = option;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
