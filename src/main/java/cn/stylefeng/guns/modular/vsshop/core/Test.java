package cn.stylefeng.guns.modular.vsshop.core;

import cn.stylefeng.guns.modular.vsshop.common.OKHttpHelper;
import cn.stylefeng.guns.modular.vsshop.model.HttpVssUser;
import cn.stylefeng.guns.modular.vsshop.model.VssUser;
import okhttp3.OkHttpClient;

public class Test {
    public static void main(String[] args) {
        CsdnHandleDownload csdnHandleDownload=new CsdnHandleDownload();
        OkHttpClient okHttpClient=OKHttpHelper.buildOKHttpClient().build();
        String resourceUrl="https://download.csdn.net/index.php/mobile/source/do_download/10772299/qq_38867366";
        VssUser vssUser=new VssUser();
        vssUser.setCookie("Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=1788*1*PC_VC; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1552236179; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1552234742,1552234780,1552236116,1552236179; dc_tos=po5t3m; UserName=qq_40087433; UserToken=9235fc18936447288297add8835d6391; ci_session=a%3A6%3A%7Bs%3A10%3A%22session_id%22%3Bs%3A32%3A%22c609e856e37678498273f4859248390b%22%3Bs%3A10%3A%22ip_address%22%3Bs%3A13%3A%2214.153.78.232%22%3Bs%3A10%3A%22user_agent%22%3Bs%3A120%3A%22Mozilla%2F5.0+%28iPhone%3B+CPU+iPhone+OS+12_1_2+like+Mac+OS+X%29+AppleWebKit%2F605.1.15+%28KHTML%2C+like+Gecko%29+Mobile%2F16C104+CSDNApp%2F%22%3Bs%3A13%3A%22last_activity%22%3Bi%3A1552234758%3Bs%3A9%3A%22user_data%22%3Bs%3A0%3A%22%22%3Bs%3A8%3A%22userInfo%22%3Ba%3A8%3A%7Bi%3A0%3Bs%3A8%3A%2269986319%22%3Bi%3A1%3Bs%3A11%3A%22qq_40087433%22%3Bi%3A2%3Bs%3A60%3A%22%242a%2411%24oppFAb78yOwBYy3lVMFg9evSzdkcs8IRMrmR.lEe6ixAOCXQdiIwS%22%3Bi%3A3%3Bs%3A16%3A%22805014823%40qq.com%22%3Bi%3A4%3Bs%3A1%3A%221%22%3Bi%3A5%3Bs%3A1%3A%220%22%3Bi%3A6%3Bs%3A28%3A%22Sun+Sep+03+20%3A06%3A23+CST+2017%22%3Bi%3A7%3Bs%3A1%3A%220%22%3B%7D%7Dd8377c730973ff6f613b92393a4cbcc6; dc_session_id=10_1552234670972.815871; uuid_tt_dd=10_2449282320-1552234670972-426169");
        HttpVssUser httpVssUser=new HttpVssUser(okHttpClient,vssUser);

        csdnHandleDownload.download(httpVssUser,resourceUrl);
    }
}
