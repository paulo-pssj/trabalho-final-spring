package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;


    public void sendEmail(ClienteDTO cliente,String emailBase,String assunto) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(cliente.getEmail());
            mimeMessageHelper.setSubject(assunto);
            mimeMessageHelper.setText(createFromTemplate(cliente,"email-template.ftl", emailBase), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }


    public String createFromTemplate(ClienteDTO cliente,String emailTemplate,String base) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", cliente.getNome());
        dados.put("from", from);
        dados.put("base", base);
        Template template = fmConfiguration.getTemplate(emailTemplate);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}