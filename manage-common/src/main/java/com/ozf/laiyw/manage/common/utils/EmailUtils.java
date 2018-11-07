package com.ozf.laiyw.manage.common.utils;

import com.ozf.laiyw.manage.common.commons.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/7 1:03
 */
public class EmailUtils {

    private static final Logger logger = Logger.getLogger(EmailUtils.class);

    public static String testConnection(String host, Integer port, String username, String password) {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);
            mailSender.testConnection();
            return "success";
        } catch (Exception e) {
            if (e.getMessage().contains("535 Error")) {
                return "用户名/授权码错误";
            } else {
                return "邮件服务不可用";
            }
        }
    }

    public static JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SystemConfig.getHost());
        mailSender.setPort(SystemConfig.getPort());
        mailSender.setUsername(SystemConfig.getUsername());
        mailSender.setPassword(SystemConfig.getPassword());
        return mailSender;
    }

    public static void send(String to, String verificationCode) {
        try {
            JavaMailSender mailSender = getJavaMailSender();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(SystemConfig.getUsername());
            helper.setTo(to);
            helper.setSubject("邮箱验证");

            String html = "<html><head>" +
                    "</head><body>" +
                    "<p>你好！</p>" +
                    "<p>验证码：" + verificationCode + "</p>" +
                    "<p>该验证码仅限本次操作有效。为了保障您的账户安全，请勿向他人泄漏验证码信息。</p>" +
                    "</body></html>";

            helper.setText(html, true);

            mailSender.send(mimeMessage);
            logger.debug("邮件发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("邮件发送失败", e);
        }
    }
}
