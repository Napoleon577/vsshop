package cn.stylefeng.guns.modular.vsshop.core;

import cn.stylefeng.guns.modular.vsshop.model.DownloadInfo;
import cn.stylefeng.guns.modular.vsshop.model.HttpVssUser;

public interface HandleDownload {
    public Boolean login(HttpVssUser httpVssUser);
    public String download(HttpVssUser httpVssUser,String resouceUrl);
    public DownloadInfo getDownloadInfo(String originUrl);
    public Boolean keepActive(HttpVssUser httpVssUser);
}
