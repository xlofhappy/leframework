package com.qweather.leframework.core.email;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;

public class LeMailSupport {

    protected final JavaMailSender javaMailSender;
    protected final MailProperties mailProperties;

    public LeMailSupport(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    protected String getUrl() {
        return mailProperties.getProperties().get("site.url");
    }

    protected String getFrom() {
        return mailProperties.getProperties().get("mail.from");
    }


}
