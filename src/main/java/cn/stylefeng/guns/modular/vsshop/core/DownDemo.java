package cn.stylefeng.guns.modular.vsshop.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DownDemo {
    public static void main(String[] args) {

    }

    public String download(String resourceUrl, String cookie) {
        Request.Builder builder=new Request.Builder();
        builder.url(resourceUrl);
        //https://download.csdn.net/index.php/mobile/source/do_download/9881373/icaoweiwei
        Map<String,String> map=new HashMap<>();
        map.put("Accept","application/json, text/plain, */*");
        map.put("Connection","keep-alive");
        map.put("Cookie",cookie);
        map.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36");
        map.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        map.put("Referer",resourceUrl);
        map.put("Accept-Encoding","br,deflate");
        Headers headers=Headers.of(map);

        try {
            //获取文件地址
            Request request=builder.build();
            Response response=http(httpVssUser,request);

            //下载文件
            String fileName= StrUtil.subBetween(response.headers().get("Content-Disposition"),"filename=\"","\"");
            Date date=new Date();
            String year=String.valueOf(DateUtil.year(date));
            String month=String.valueOf(DateUtil.month(date)+1);
            String day=String.valueOf(DateUtil.dayOfMonth(date));

            FileUtil.writeBytes(response.body().bytes(),rootPath+ File.separator+"files"+File.separator+year+File.separator+month+File.separator+day+File.separator+fileName);
            return "files/"+year+"/"+month+"/"+day+"/"+fileName;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
