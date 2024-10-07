package cn.stylefeng.guns.modular.supermms;

public class MmsContentTemplete {
    private String mms;//	是	MmsContent	超级短信内容，详见以下mmsContent描述MmsContent对象需先进行json格式化JSONObject.toJSONString(mmsContent, SerializerFeature.SortField)

    private String mmsFile;//	mmsFile	是	String	接入方的标准超级短信包。超级短信包转为HEX16位字符串Hex.encodeHexString(FileUtils.readFileToByteArray(xxx.zip));

    public String getMmsFile() {
        return mmsFile;
    }

    public void setMmsFile(String mmsFile) {
        this.mmsFile = mmsFile;
    }

    public String getMms() {
        return mms;
    }

    public void setMms(String mms) {
        this.mms = mms;
    }
}
