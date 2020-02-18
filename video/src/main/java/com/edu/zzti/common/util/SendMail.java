package com.edu.zzti.common.util;

import com.edu.zzti.common.util.mail.MailSenderInfo;
import com.edu.zzti.common.util.mail.SimpleMailSender;

/**
 * 发送邮件
 */
public class SendMail {

    public static boolean sendalert(String title,String content) {
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("lovsvol@163.com");
        mailInfo.setPassword("lovsvol123");//您的邮箱密码
        mailInfo.setFromAddress("lovsvol@163.com");
        mailInfo.setToAddress("747319155@qq.com");
        mailInfo.setSubject(title);
        mailInfo.setContent(content);
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        /* sms.sendTextMail(mailInfo);//发送文体格式*/
        if (sms.sendHtmlMail(mailInfo)){//发送html格式
            return true;
        }else{
            return false;
        }
    }
}
