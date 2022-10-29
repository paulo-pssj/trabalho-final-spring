package br.com.shinigami.service;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
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
    private final ClienteService clienteService;
    private final JavaMailSender emailSender;

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(TO);
        message.setSubject("Assunto");
        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);

        helper.setFrom(from);
        helper.setTo(TO);
        helper.setSubject("Subject");
        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");

        File file1 = new File("imagem.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);

        emailSender.send(message);
    }
//
    public void sendEmail(ClienteDTO cliente, String base) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(cliente.getEmail());
            mimeMessageHelper.setSubject("Seu cadastro foi concluido com sucesso!");
            mimeMessageHelper.setText(createFromTemplate(cliente,"email-template.ftl",base), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(ContratoDTO contrato, String base) throws RegraDeNegocioException {
        ClienteDTO locador = clienteService.buscarCliente(contrato.getLocador().getIdCliente());
        ClienteDTO locatario = clienteService.buscarCliente(contrato.getLocatario().getIdCliente());
        sendEmail(locador,base);
        sendEmail(locatario,base);
    }

    public String createFromTemplate(ClienteDTO cliente,String emailTemplate,String base) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", cliente.getNome());
        dados.put("from", from);
        dados.put("id", cliente.getIdCliente());
        Template template = fmConfiguration.getTemplate(emailTemplate);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}