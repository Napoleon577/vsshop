package cn.stylefeng.guns.modular.supermms;

import java.util.List;

public class MmsContent {

    private List<MmsFrame> frames;//	MmsFrame[]	+	是	超级短信帧信息
    private String subject;//	String	9	否	超级短信标题，
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<MmsFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<MmsFrame> frames) {
        this.frames = frames;
    }
}
