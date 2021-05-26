package com.tplate.layers.business.services.authService;

import com.tplate.layers.business.exceptions.auth.EmailSenderException;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final FreeMarkerConfigurer freemarkerConfig;

    public EmailService(JavaMailSender javaMailSender, FreeMarkerConfigurer freemarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    public void send(IEmail emailDto) throws EmailSenderException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            String templateContent = FreeMarkerTemplateUtils
                    .processTemplateIntoString(freemarkerConfig.getConfiguration()
                                    .getTemplate("/email/reset-password.ftl"),
                            emailDto.getData());

            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(templateContent, true);


            javaMailSender.send(mimeMessage);

            log.info("Email was sent successfully");


        } catch (MessagingException | TemplateException | IOException | RuntimeException e) {
            log.error("Email not sent. {} {}", e.getMessage(), e.getClass().getCanonicalName());
            EmailSenderException.throwsException(e.getMessage());
        }


    }
}
