// ~ CopyRight © 2012 USTC SINOVATE  SOFTWARE CO.LTD All Rights Reserved.

package com.scnjwh.intellectreport.common.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * 邮箱发送辅助类
 * @author skycc
 * @date 2014-11-16
 * @modifier zhouxin
 * @modifiedTime 2018-02-06
 */
public class MailUtil {
    private static final String PROPERTIES_FILE_NAME = "jeesite.properties";
    //邮件服务器主机:默认为163
    private static String MAIL_HOST = "smtp.163.com";
    //邮件服务器端口:默认为465
    private static String MAIL_PORT = "465";
    //邮件发送者邮箱
    private static String MAIL_SENDER;
    //邮件发送者
    private static String MAIL_USER;
    //邮件第三方登录授权码(需开通POP3/SMTP、IMAP/SMTP 服务)
    private static String MAIL_PASSWORD;

    /**
     * 查询配置文件设置邮件发送的一些参数
     * @throws IOException io异常
     */
    private static void setProperty () throws MessagingException {

        InputStream is = null;

        try {
            is = MailUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
            Properties pro = new Properties();
            pro.load(is);
            MAIL_HOST = pro.getProperty("mail.smtpHost");
            MAIL_SENDER = pro.getProperty("mail.sender");
            MAIL_USER = pro.getProperty("mail.user");
            if(MAIL_USER == null || "".equals(MAIL_USER.trim())){
                MAIL_USER = MAIL_SENDER.split("@")[0];
            }
            MAIL_PASSWORD = pro.getProperty("mail.pwd");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessagingException("初始化参数异常！");
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 验证邮箱地址是否合格
     * @param email 邮箱地址
     * @return 是否合格
     */
    public static boolean checkEmail(String email){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }

    /**
     * 设置邮件接收人
     * @param msg 邮件消息类
     * @param receiver 接收人
     * @throws MessagingException 邮件设置接收地址异常
     */
    private static void setReceiver(MimeMessage msg,String receiver) throws MessagingException {
        if(receiver == null || "".equals(receiver.trim())){
            throw new MessagingException("收信人不能为空！");
        }
        if(receiver.contains(",")){
            String[] receivers = receiver.split(",");
            InternetAddress[] to = new InternetAddress[receivers.length];
            for (int i = 0; i < receivers.length; i++) {
                to[i] = new InternetAddress(receivers[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, to);
        }else {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        }
    }

    /**
     * 设置邮件发送抄送人
     * @param msg 邮件消息类
     * @param copy 抄送人
     * @throws MessagingException 邮件设置抄送地址异常
     */
    private static void setCopy(MimeMessage msg,String copy) throws MessagingException {
        if(copy == null || "".equals(copy.trim())){
            return;
        }
        if(copy.contains(",")){
            String[] copys = copy.split(",");
            InternetAddress[] cc = new InternetAddress[copys.length];
            for (int i = 0; i < copys.length; i++) {
                cc[i] = new InternetAddress(copys[i]);
            }
            msg.setRecipients(Message.RecipientType.CC, cc);
        }else {
            msg.setRecipient(Message.RecipientType.CC, new InternetAddress(copy));
        }
    }

    /**
     * 发送电子邮件
     *
     * @param receiver 邮件接收者
     * @param copy     抄送列表
     * @param title    邮件的标题
     * @param content  邮件的内容
     * @throws Exception
     */
    public static void sendEmail(String receiver, String copy, String title, String content) throws MessagingException {
        setProperty();
        Properties props = new Properties();
        props.put("mail.host", MAIL_HOST);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", MAIL_PORT);
        props.put("mail.smtp.port", MAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        Session s = Session.getDefaultInstance(props);
        s.setDebug(true);
        MimeMessage message = new MimeMessage(s);
        // 给消息对象设置发件人/收件人/主题/发信时间
        // 发件人的邮箱
        InternetAddress from = new InternetAddress(MAIL_SENDER);
        message.setFrom(from);
        //设置收件人
        setReceiver(message,receiver);
        //设置抄送人
        setCopy(message,copy);
        //设置邮件主题
        message.setSubject(title);
        //设置邮件发送日期
        message.setSentDate(new Date());
        // 给消息对象设置内容
        BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
        mdp.setContent(content, "text/html;charset=gb2312");// 给BodyPart对象设置内容和格式/编码方式防止邮件出现乱码
        Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对
        // 象(事实上可以存放多个)
        mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
        message.setContent(mm);// 把mm作为消息对象的内容

        message.saveChanges();
        Transport transport = s.getTransport("smtp");
        transport.connect(MAIL_HOST, MAIL_USER, MAIL_PASSWORD);// 设置发邮件的网关，发信的帐户和密码，这里修改为您自己用的
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static void sendEmail(String receiver, String title, String content) throws MessagingException {
        sendEmail(receiver,null,title,content);
    }

    public static void main(String[] args) {
        try {
            sendEmail("295179392@qq.com", null,"系统测试邮件",
                    "hi,all,I am AnChao!111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
