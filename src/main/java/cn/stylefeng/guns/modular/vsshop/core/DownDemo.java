package cn.stylefeng.guns.modular.vsshop.core;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class DownDemo {
    // 提取 sourceId 的方法
    public static String extractSourceId(String url) {
        // 正则表达式用于匹配 URL 结尾的数字
        String regex = "(?<=\\/)\\d+(?=\\?)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        // 查找并返回匹配的 sourceId
        if (matcher.find()) {
            return matcher.group();  // 返回匹配的数字部分
        }
        return null;  // 如果未找到 sourceId，返回 null
    }
    private static final OkHttpClient client = new OkHttpClient();

    // 获取真实下载地址的方法
    public static String getRealDownloadUrl(String downloadUrl, String cookie,String sourceId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 构建请求体，包含 sourceId
        JSONObject jsonRequestBody = new JSONObject();
        jsonRequestBody.put("sourceId", sourceId);  // 将 sourceId 放入请求体

        // 定义请求体的 MediaType
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        // 创建请求体
        RequestBody requestBody = RequestBody.create(JSON,jsonRequestBody.toString());

        // 构建POST请求
        Request.Builder builder = new Request.Builder();
        builder.url(downloadUrl);
        builder.post(requestBody);

        // 设置请求头
        builder.header("Accept", "application/json, text/plain, */*");
        builder.header("Accept-Encoding", "gzip"); // 请求服务器返回 Gzip 压缩的内容
        builder.header("Content-Type", "application/json");
        builder.header("Cookie", cookie);
        builder.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");

        Request request = builder.build();

        // 发送请求
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // 检查是否使用 Gzip 压缩
                String encoding = response.header("Content-Encoding");
                InputStream responseStream = response.body().byteStream();

                if ("gzip".equalsIgnoreCase(encoding)) {
                    responseStream = new GZIPInputStream(responseStream);
                }

                // 读取响应内容
                String responseBody = readInputStream(responseStream);
                System.out.println("Response Body: " + responseBody); // 打印响应内容以调试

//                // 检查返回内容是否以 "{" 开始
//                if (responseBody.trim().startsWith("{")) {
//                    // 使用 org.json 解析返回的响应
//                    JSONObject jsonObject = new JSONObject(responseBody);
//                    if (jsonObject.get) {
//                        // 提取 data 字段中的真实下载地址
//                        return jsonObject.getString("data");
//                    } else {
//                        System.out.println("请求失败，错误信息：" + jsonObject.getString("message"));
//                    }
//                } else {
//                    System.out.println("返回的响应不是一个有效的 JSON 对象！");
//                }
            }
        }
        return null;
    }

    // 方法：读取输入流内容并转换为字符串
    private static String readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toString("UTF-8");
    }


    // 提取 response 中 data 字段的真实下载地址
    private String extractRealUrlFromResponse(String responseBody) {
        Pattern pattern = Pattern.compile("\"data\":\"(https?.*?)\"");
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            return matcher.group(1).replace("\\", "");  // 提取到的真实下载地址
        }
        return null;
    }
    // 下载文件并保存到D盘的方法
    public static void downloadFile(String realDownloadUrl, String fileName) throws IOException {
        Request request = new Request.Builder()
                .url(realDownloadUrl)
                .build();

        // 发送请求并下载文件
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // 获取文件流
                try (InputStream inputStream = response.body().byteStream();
                     FileOutputStream outputStream = new FileOutputStream("D:\\" + fileName)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            } else {
                System.out.println("下载失败，状态码: " + response.code());
            }
        }
    }
    public static void main(String[] args) throws IOException {
        // 示例下载地址
        String resourceUrl = "https://download.csdn.net/download/qq_24044725/10376138?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522EEDE26DB-8F8B-4F06-B91C-CF369039BC8D%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fdownload.%2522%257D&request_id=EEDE26DB-8F8B-4F06-B91C-CF369039BC8D&biz_id=1&utm_medium=distribute.pc_search_result.none-task-download-2~download~first_rank_ecpm_v1~rank_v31_ecpm-2-10376138-null-null.269^v2^control&utm_term=java%E6%96%87%E4%BB%B6%E4%B8%8B%E8%BD%BD&spm=1018.2226.3001.4451.3";
        String downloadUrl = "https://download.csdn.net/api/source/detail/v1/download";
        String sourceId = extractSourceId(resourceUrl);
        String cookie = "uuid_tt_dd=10_19748072640-1729517290781-551554; fid=20_44117605514-1729517292347-041966; _gid=GA1.2.1184290370.1729517294; loginbox_strategy=%7B%22taskId%22%3A349%2C%22abCheckTime%22%3A1729517300038%2C%22version%22%3A%22exp11%22%2C%22blog-threeH-dialog-exp11tipShowTimes%22%3A2%2C%22blog-threeH-dialog-exp11%22%3A1729517300040%7D; UN=Napoleon_skuky; csdn_newcert_Napoleon_skuky=1; __gads=ID=26b7837bf2415c72:T=1729517294:RT=1729560175:S=ALNI_Ma50Qj__aGRQyHoCDxtJBKz4KutMw; __gpi=UID=00000f4de59e8c36:T=1729517294:RT=1729560175:S=ALNI_MYrFry-g8e9TB7JyLKasktmekzSAA; __eoi=ID=3290e115cd2d08f4:T=1729517294:RT=1729560175:S=AA-AfjZHQhMVVWdKuL1jHvQiwDgT; FCNEC=%5B%5B%22AKsRol-z9_tk0HuZPYGP22zutti4tyis85GivjeJ4x3b-Y1mhFa97N-PYJZpsvysVv_gyrxRUKgBH5iaiD15zk3Jy9FqXYrO6nEbgosO4hHETEzrQV8wBI4YtQpFYAI933Rp3VP9LNLtxkf3XXzzyt2WisjO1ZQ6uQ%3D%3D%22%5D%5D; is_advert=1; creative_btn_mp=3; UserName=Napoleon_skuky; UserInfo=e9f5106e0ed74f81901ef604d36fb944; UserToken=e9f5106e0ed74f81901ef604d36fb944; UserNick=Napoleon577; AU=74B; BT=1729565213807; p_uid=U110000; tfstk=gbGxgWZeH3x020_P_G9uIJwRNZ8o6f3qnmuCslqcC0n-7cCci5V0CPZ-bsciSVMO62rPiqcmSZkrx21miKPG6SPa1HxHtBbqu5P_AdMU_iS75yOGG1ajuFoIV0KHtB0XSrJAMHccM-A7xPN_l-6XNUU3-56f1cZ7Fyahc5NsfUQ7RP5b551_F4Zz55Zsf5M1zEEGlk5tQarFp4CRY1CtwreYf1qR60h5t-ZIeuC6nbUAfkgb211_muV4O43DD6uugXoL-mA5MDnjVXeIZ35gfJHIwoGWAdFSemlTKfbhs43oMVH_eEOSPSq0Lbel5tFxCmlT3xQw84Nj4vrUhL-zyjc3BkyRVngoyowKBmxP1oDKP0enZgf3FbgjG-lM21iC46Gn9O9hxkUGhULRQOybzDtKzL1OzRGLykYkEOWaezz8xULRQOybzzEHzbWNQ8UP.; historyList-new=%5B%5D; c_dl_fref=https://so.csdn.net/so/search; dc_session_id=10_1729644057311.146417; c_first_ref=www.google.com; c_segment=2; dc_sid=7ae2e1b0e457b4813ba04b07c02dfe22; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1729561189,1729564518,1729588494,1729644069; HMACCOUNT=888F7B4494651FFE; c_hasSub=true; _clck=1c6fyjf%7C2%7Cfq9%7C0%7C1755; c_first_page=https%3A//blog.csdn.net/king0406/article/details/102655347; c_dsid=11_1729644102890.087006; _ga=GA1.1.2044956274.1729517294; _clsk=10e4npn%7C1729644122567%7C2%7C0%7Cx.clarity.ms%2Fcollect; _ga_7W1N0GEY1P=GS1.1.1729644071.6.1.1729644490.60.0.0; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20231011044944.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A0%2C%22type%22%3A0%2C%22oldUser%22%3Afalse%2C%22useSeven%22%3Atrue%2C%22oldFullVersion%22%3Afalse%2C%22userName%22%3A%22Napoleon_skuky%22%7D; fe_request_id=1729644515192_0046_0510421; csrfToken=b3TApWBL0ZWxvTriwqCgtlXd; c_pref=https%3A//i.csdn.net/; c_ref=https%3A//so.csdn.net/so/search%3Fspm%3D1010.2135.3001.4501%26q%3Djava%25E6%2596%2587%25E4%25BB%25B6%25E4%25B8%258B%25E8%25BD%25BD%26t%3Ddoc%26u%3D; c_utm_term=java%E6%96%87%E4%BB%B6%E4%B8%8B%E8%BD%BD; c_utm_relevant_index=2; relevant_index=2; c_dl_prid=1729644527857_807407; c_dl_rid=1729644541263_767297; c_dl_fpage=/download/qq_24044725/10376138; c_dl_um=distribute.pc_search_result.none-task-download-2%7Edownload%7Efirst_rank_ecpm_v1%7Erank_v31_ecpm-1-85051766-null-null.269%5Ev2%5Econtrol; c_utm_medium=distribute.pc_search_result.none-task-download-2%7Edownload%7Efirst_rank_ecpm_v1%7Erank_v31_ecpm-2-10376138-null-null.269%5Ev2%5Econtrol; c_page_id=default; log_Id_pv=69; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1729644557; https_waf_cookie=6173a7a9-98bc-47270f328a31a6839e6bd2b035bae915e1e8; log_Id_click=128; log_Id_view=2719; dc_tos=slsabp";

        if (sourceId != null) {
            // 获取真实下载地址
            String realDownloadUrl = getRealDownloadUrl(downloadUrl, cookie,sourceId);
            if (realDownloadUrl != null) {
                // 下载文件并保存到D盘
                downloadFile(realDownloadUrl, "downloaded_file.java");
            }
        } else {
            System.out.println("无法提取sourceId");
        }
    }
}
