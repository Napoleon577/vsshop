package cn.stylefeng.guns.modular.supermms;

import java.security.PrivateKey;

public class MmsAttachment {

   private String fileName;//	String	255	是	该帧的超级短信文件名
    private Integer index;//	Integer	11	是	文件顺序
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
