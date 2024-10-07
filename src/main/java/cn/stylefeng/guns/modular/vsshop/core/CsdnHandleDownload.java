package cn.stylefeng.guns.modular.vsshop.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.modular.vsshop.model.DownloadInfo;
import cn.stylefeng.guns.modular.vsshop.model.HttpVssUser;
import cn.stylefeng.guns.modular.vsshop.model.VssUser;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CsdnHandleDownload extends BasicHandleDownload implements HandleDownload{
    private static Logger logger=LoggerFactory.getLogger(CsdnHandleDownload.class);

    @Override
    public Boolean login(HttpVssUser httpVssUser) {

        return null;
    }

    @Override
    public String download(HttpVssUser httpVssUser,String resouceUrl) {
        Request.Builder builder=new Request.Builder();
        builder.url(resouceUrl);
        //https://download.csdn.net/index.php/mobile/source/do_download/9881373/icaoweiwei
        Map<String,String> map=new HashMap<>();
        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put("Connection","keep-alive");
        map.put("Cookie",httpVssUser.getVssUser().getCookie());
        map.put("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16C104 CSDNApp/3.5.1(iOS)");
        map.put("Accept-Language","zh-cn");
        map.put("Referer",resouceUrl);
        map.put("Accept-Encoding","br,deflate");
        Headers headers=Headers.of(map);
        builder.headers(headers);

        try {
            //获取文件地址
            Request request=builder.build();
            Response response=http(httpVssUser,request);

            //下载文件
            String fileName=StrUtil.subBetween(response.headers().get("Content-Disposition"),"filename=\"","\"");
            Date date=new Date();
            String year=String.valueOf(DateUtil.year(date));
            String month=String.valueOf(DateUtil.month(date)+1);
            String day=String.valueOf(DateUtil.dayOfMonth(date));

            FileUtil.writeBytes(response.body().bytes(),rootPath+File.separator+"files"+File.separator+year+File.separator+month+File.separator+day+File.separator+fileName);
            return "files/"+year+"/"+month+"/"+day+"/"+fileName;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public DownloadInfo getDownloadInfo(String originUrl) {
        try {
            //获取文件地址
            Document doc = Jsoup.connect(originUrl).get();
            String coin= doc.select(".dl_download_box").select("label").select("em").text();
            DownloadInfo downloadInfo=new DownloadInfo();
            downloadInfo.setCoin(coin);
            return downloadInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean keepActive(HttpVssUser httpVssUser) {
        return null;
    }

}
