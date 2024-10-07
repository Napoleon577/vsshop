package cn.stylefeng.guns.modular.vsshop.common;

import cn.hutool.extra.mail.MailUtil;
import cn.stylefeng.guns.modular.vsshop.model.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailHelper {
    private static Logger logger=LoggerFactory.getLogger(EmailHelper.class);

    public static Boolean send(Email email){
        try {
            MailUtil.send("vsshop@aliyun.com", email.getTitle(), email.getContent(), false);
            logger.error("邮件发送成功: "+email.getContent());

            return true;
        } catch (Exception e) {
            logger.error("邮件发送失败: "+e.getMessage());
            return false;
        }

    }
}
