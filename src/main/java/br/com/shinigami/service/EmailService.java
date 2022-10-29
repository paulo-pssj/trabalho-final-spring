package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.Cliente;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "lucas.vieira@dbccompany.com.br";
    private final JavaMailSender emailSender;


    public void sendEmail(ClienteDTO cliente) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            String emailBase = "Parabéns, Seu cadastro foi concluido com sucesso! Seu id é: "+cliente.getIdCliente();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(cliente.getEmail());
            mimeMessageHelper.setSubject("Seu cadastro foi concluido com sucesso!");
            mimeMessageHelper.setText(createFromTemplate(cliente,"email-template.ftl", emailBase), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailContrato(ContratoDTO contrato) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            String emailBase = "Contrato Criado com sucesso! <br> Contrato entre locador: "+contrato.getLocador().getNome()+" e locatario: "+contrato.getLocatario().getNome()+"<br>"+
                    "Valor Mensal: R$"+contrato.getValorAluguel();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(new String[]{contrato.getLocador().getEmail(), contrato.getLocatario().getEmail()});
            mimeMessageHelper.setSubject("Seu contrato foi gerado com sucesso!!");
            mimeMessageHelper.setText(createFromTemplate("email-template.ftl", emailBase), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }
    public String createFromTemplate(String emailTemplate,String base) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("base", base);
        dados.put("from", from);
        Template template = fmConfiguration.getTemplate(emailTemplate);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
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