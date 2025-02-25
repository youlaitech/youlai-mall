package com.youlai.common.mail.service;

/**
 * 邮件服务接口层
 *
 * @author Ray.Hao
 * @since 2024/8/17
 */
public interface MailService {


    /**
     * 发送简单文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    void sendMail(String to, String subject, String text) ;

    /**
     * 发送带附件的邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param filePath 附件路径
     */
    void sendMailWithAttachment(String to, String subject, String text, String filePath);

}
