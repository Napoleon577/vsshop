package cn.stylefeng.guns.modular.vsshop.model;

import okhttp3.OkHttpClient;

/**
 * 每个用户一个httpk客户端，便于维持cookie
 */
public class HttpVssUser {
   private OkHttpClient okHttpClient;
   private VssUser vssUser;
   //连续失败尝试登录次数，成功清0
   private Integer tryConut;
   public HttpVssUser(OkHttpClient okHttpClient,VssUser vssUser){
       this.okHttpClient=okHttpClient;
       this.vssUser=vssUser;
   }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public VssUser getVssUser() {
        return vssUser;
    }

    public void setVssUser(VssUser vssUser) {
        this.vssUser = vssUser;
    }

    public Integer getTryConut() {
        return tryConut;
    }

    public void setTryConut(Integer tryConut) {
        this.tryConut = tryConut;
    }
}
