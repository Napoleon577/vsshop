package cn.stylefeng.guns.modular.supermms;

/**
 * Created by jianyinlin on 2019/3/18
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import sun.misc.BASE64Encoder;

public class MD5Util {
    /**
     *
     * @param pwd
     *            需要加密的字符串
     * @param isUpper
     *            字母大小写(false为默认小写，true为大写)
     * @param bit
     *            加密的类型（16,32,64）
     * @return
     */
    public static String getMD5(String pwd, boolean isUpper, Integer bit) {
        String md5 = new String();
        try {
            // 创建加密对象
            MessageDigest md = MessageDigest.getInstance("md5");
            if (bit == 64) {
                BASE64Encoder bw = new BASE64Encoder();
                String bsB64 = bw.encode(md.digest(pwd.getBytes("utf-8")));
                md5 = bsB64;
            } else {
                // 计算MD5函数
                md.update(pwd.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuffer sb = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        sb.append("0");
                    sb.append(Integer.toHexString(i));
                }
                md5 = sb.toString();
                if(bit == 16) {
                    //截取32位md5为16位
                    String md16 = md5.substring(8, 24).toString();
                    md5 = md16;
                    if (isUpper)
                        md5 = md5.toUpperCase();
                    return md5;
                }
            }
            //转换成大写
            if (isUpper)
                md5 = md5.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("md5加密抛出异常！");
        }

        return md5;
    }

    public static void main(String[] args) throws Exception {
        String a = "{\"content\":{\"password\":\"222\",\"username\":\"111\"},\"option\":{\"reqtime\":1536551693557}}";
        String md5a = getMD5(a, false, 64);
        System.out.println(md5a);
        System.out.println(Base64.getEncoder().encodeToString(md5a.getBytes()));
        System.out.println(md5a.length());
    }

    public static String getMD5Base64(String str) throws Exception {
        try {
            // 创建加密对象
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes=str.getBytes();
            byte[] mdDi=md.digest(bytes);
            return new String(Base64.getEncoder().encode(mdDi),"UTF-8");
         }catch (Exception e){
            throw e;
        }
    }
}
