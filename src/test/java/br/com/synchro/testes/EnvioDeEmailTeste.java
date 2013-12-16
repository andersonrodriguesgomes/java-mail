package br.com.synchro.testes;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnvioDeEmailTeste {
	public void enviarMensagem() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.synchro.com.br");
		Session s = Session.getInstance(props, null);

		Message message = new MimeMessage(s);
		// Estipula quem esta enviando

		InternetAddress from;
		try {
			from = new InternetAddress("anderson.gomes@synchro.com.br");

			message.setFrom(from);

			// Estipula para quem será enviado
			InternetAddress to = new InternetAddress("recforsap@synchro.com.br");
			message.addRecipient(Message.RecipientType.TO, to);
			message.setSubject("Teste de Envio");

			MimeMultipart mpRoot = new MimeMultipart("mixed");
			MimeMultipart mpContent = new MimeMultipart("alternative");
			MimeBodyPart contentPartRoot = new MimeBodyPart();
			contentPartRoot.setContent(mpContent);
			mpRoot.addBodyPart(contentPartRoot);

			// enviando texto
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("Testando");
			mpContent.addBodyPart(mbp1);

			// enviando html
			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.setContent("<P> Teste de envio HTML </P>", "text/html");
			mpContent.addBodyPart(mbp2);

			// enviando anexo
			URL in = this.getClass().getResource(
					"13130384534924000168550010000056271021202346.xml");

			MimeBodyPart mbp3 = new MimeBodyPart();
			DataSource fds = new FileDataSource(new File(in.getFile()));
			mbp3.setDisposition(Part.ATTACHMENT);
			mbp3.setDataHandler(new DataHandler(fds));
			mbp3.setFileName(fds.getName());

			mpRoot.addBodyPart(mbp3);

			message.setContent(mpRoot);
			message.saveChanges();

			Transport.send(message);

		} catch (Exception e) {
			throw new IllegalArgumentException("Ocorreu um erro inesperado: "
					+ e);

		}
	}
}
