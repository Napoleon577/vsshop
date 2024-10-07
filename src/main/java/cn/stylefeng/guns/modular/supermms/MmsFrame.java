package cn.stylefeng.guns.modular.supermms;

import java.util.List;

public class MmsFrame {

    private List<MmsAttachment> attachments;//	MmsAttachment[]	+	是	超级短信文件信息数组
    private Integer index;//	Integer	11	是	帧顺序
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<MmsAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MmsAttachment> attachments) {
        this.attachments = attachments;
    }
}
