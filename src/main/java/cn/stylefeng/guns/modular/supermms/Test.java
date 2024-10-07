package cn.stylefeng.guns.modular.supermms;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.modular.vsshop.common.OKHttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.omg.CORBA.DATA_CONVERSION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    private static final OkHttpClient okHttpClient= OKHttpHelper.buildOKHttpClient().build();
    public static String URL="http://203.130.42.147:9000";
    public static String TOKEN;
    public static void main(String[] args) throws Exception {

//        String proxyHost = "127.0.0.1";
//        String proxyPort = "1080";
//
//        System.setProperty("http.proxyHost", proxyHost);
//        System.setProperty("http.proxyPort", proxyPort);
//
//// 对https也开启代理
//        System.setProperty("https.proxyHost", proxyHost);
//        System.setProperty("https.proxyPort", proxyPort);
        String username="119742381928";
        String password="ylm@123";
        login(username,password);
        send();
    }

    public static void login(String username,String password) throws Exception {
        MmsTemplate mmsTemplateTest=new MmsTemplate();
        JSONObject contenJsonObject=new JSONObject(false);
        contenJsonObject.put("username",username);
        contenJsonObject.put("password",password);

        JSONObject optionJsnObject=new JSONObject();
        optionJsnObject.put("reqtime",System.currentTimeMillis());

        mmsTemplateTest.setId(null);
        mmsTemplateTest.setContent(contenJsonObject);
        mmsTemplateTest.setOption(optionJsnObject);

        String data=SignUtil.getSigned(mmsTemplateTest);

        Request.Builder builder=new Request.Builder();
        builder.url(String.format("%s/api/v1/user/token", URL));
        //https://download.csdn.net/index.php/mobile/source/do_download/9881373/icaoweiwei
        Map<String,String> map=new HashMap<>();
        Headers headers=Headers.of(map);
        builder.headers(headers);
        builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),data));
        Response response=okHttpClient.newCall(builder.build()).execute();
        TOKEN=JSON.parseObject(response.body().string()).getJSONObject("content").getJSONObject("result").getString("token");
    }

    public static void send() throws Exception {
        MmsTemplate mmsTemplate=new MmsTemplate();
        JSONObject id=new JSONObject(true);
        id.put("mmsId","20526");
        id.put("productId","487");

        mmsTemplate.setId(id);

        String time=System.currentTimeMillis()+"";
        JSONObject content=new JSONObject(true);
//        content.put("sendTime",time);
        mmsTemplate.setContent(content);

        JSONObject option=new JSONObject(true);
        option.put("mobileFile",Hex.encodeHexString(FileUtil.readBytes("C:\\Users\\lin\\Desktop\\1.txt")));
        option.put("reqtime",time);
        option.put("taskType","0");

        mmsTemplate.setOption(option);

        String data=SignUtil.getSigned(mmsTemplate);
        System.out.println(data);
        Request.Builder builder=new Request.Builder();

        builder.url(String.format("%s/api/v1/sendTask/sendMms", URL));
        //https://download.csdn.net/index.php/mobile/source/do_download/9881373/icaoweiwei
        Map<String,String> map=new HashMap<>();
        map.put("Authorization",TOKEN);
        Headers headers=Headers.of(map);
        builder.headers(headers);
        builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),data));
        Response response=okHttpClient.newCall(builder.build()).execute();
        System.out.println(response.body().string());


    }

    public String create() throws Exception{
        MmsTemplate mmsTemplate=new MmsTemplate();
        MmsId mmsId=new MmsId();
        mmsId.setProductId("487");
        mmsTemplate.setId((JSONObject) JSONObject.toJSON(mmsId));

        MmsContentTemplete mmsContentTemplete=new MmsContentTemplete();
        mmsContentTemplete.setMmsFile(Hex.encodeHexString(FileUtil.readBytes("C:\\Users\\lin\\Desktop\\1.zip"))
        );
        MmsContent mmsContent=new MmsContent();
        List<MmsFrame> mmsFrameList=new ArrayList<>();
        MmsFrame mmsFrame=new MmsFrame();
        List<MmsAttachment> mmsAttachmentList=new ArrayList<>();
        MmsAttachment mmsAttachment=new MmsAttachment();
        mmsAttachment.setFileName("1.mp4");
        mmsAttachment.setIndex(0);
        mmsAttachmentList.add(mmsAttachment);
        mmsFrame.setAttachments(mmsAttachmentList);
        mmsFrame.setIndex(0);
        mmsFrameList.add(mmsFrame);
        mmsContent.setFrames(mmsFrameList);
        mmsContent.setSubject("main");
        mmsContentTemplete.setMms(JSON.toJSONString(mmsContent));

        mmsTemplate.setContent((JSONObject)JSON.toJSON(mmsContentTemplete));

        MmsOption mmsOption=new MmsOption();
        mmsOption.setReqtime(System.currentTimeMillis()+"");
        mmsTemplate.setOption((JSONObject)JSON.toJSON(mmsOption));
        String data=SignUtil.getSigned(mmsTemplate);
        System.out.println(data);
        Request.Builder builder=new Request.Builder();
        builder.url(String.format("%s/api/v1/saveMms/save", URL));
        //https://download.csdn.net/index.php/mobile/source/do_download/9881373/icaoweiwei
        Map<String,String> map=new HashMap<>();
        map.put("Authorization",TOKEN);
        Headers headers=Headers.of(map);
        builder.headers(headers);
        builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),data));
        Response response=okHttpClient.newCall(builder.build()).execute();
        return response.body().string();
    }
}
