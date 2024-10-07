package cn.stylefeng.guns.modular.vsshop.core;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.stylefeng.guns.modular.vsshop.model.HttpVssUser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;

public class BasicHandleDownload {
    private Logger logger=LoggerFactory.getLogger(BasicHandleDownload.class);
    public String rootPath=getRootPath();

    public Response http(HttpVssUser httpVssUser,Request request) throws IOException {
        OkHttpClient okHttpClient=httpVssUser.getOkHttpClient();
        return okHttpClient.newCall(request).execute();
    }
    public String getRootPath(){
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParent();
    }
}
