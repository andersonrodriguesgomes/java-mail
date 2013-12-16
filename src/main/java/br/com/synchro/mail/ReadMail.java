package br.com.synchro.mail;

//TODO REALISAR TESTES INTEGRADOS COM O SAP
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import br.com.synchro.mail.utils.Config;

public class ReadMail {

	public static final String pastaEntrada = "Inbox";
	public static final String PLAIN = "text/plain";
	public static final String HTML = "text/html";
	static StringBuilder status = new StringBuilder();

	public String executaLeituraEmail() {

		Properties systemProperties = System.getProperties();

		try {

			systemProperties.setProperty("mail.store.protocol",
					Config.props.getProperty("protocoloLeitura"));

			Session session = Session
					.getDefaultInstance(systemProperties, null);

			Store store = session.getStore(Config.props
					.getProperty("protocoloLeitura"));

			store.connect(Config.props.getProperty("servidorLeitura"),
					Config.props.getProperty("enderecoEmail"),
					Config.props.getProperty("senhaEmail"));

			Folder inbox = store.getFolder(pastaEntrada);

			inbox.open(Folder.READ_WRITE);

			Message messages[] = inbox.getMessages();

			if (messages.length > 0) {

				for (Message message : messages) {

					printMessage(message);

					if (!(Config.props.getProperty("protocoloLeitura")
							.toLowerCase().equals("pop3"))
							|| (Config.props.getProperty("protocoloLeitura")
									.toLowerCase().equals("pop"))) {

						Folder dfolder = store.getFolder(Config.props
								.getProperty("pastaProcessados"));

						inbox.copyMessages(messages, dfolder);

						status.append("\n\rE-mail copiado com sucesso para a pasta "
								+ dfolder);

					}

					message.setFlag(Flags.Flag.DELETED, true);

					status.append("\n\rE-mail apagado.");
				}

				inbox.close(true);

				store.close();

				return status.toString();

			}

			else {

				inbox.close(true);
				store.close();

				return "Conta de e-mail sem mensagens.";

			}

		} catch (Exception e) {
			throw new IllegalAccessError("Ocorreu um erro inesperado: " + e);

		}
	}

	public static void printMessage(Message message) {

		try {
			// RESPONSAVEL POR PEGAR CABECALHO DA MENSAGEM
			String from = ((InternetAddress) message.getFrom()[0])
					.getPersonal();

			if (from == null)
				from = ((InternetAddress) message.getFrom()[0]).getAddress();

			status.append("\n\rEmitente: " + from);

			String subject = message.getSubject();

			status.append("\n\rAssunto: " + subject);

			// RESPONSAVEL POR PEGAR PARTE DA MENSAGEM
			Part messagePart = message;

			Object content = messagePart.getContent();

			if (content instanceof Multipart) {

				messagePart = ((Multipart) content).getBodyPart(0);

				status.append("\n\rData e Hora Recebimento: "
						+ message.getSentDate());

			}

			// RESPONSAVEL POR PEGAR O CONTEXT TYPE
			String contentType = messagePart.getContentType();

			// System.out.println("CONTENT:" + contentType);

			if (contentType.startsWith(PLAIN) || contentType.startsWith(HTML)) {

				InputStream is = messagePart.getInputStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));

				String thisLine = reader.readLine();

				while (thisLine != null) {

					thisLine = reader.readLine();

				}

			} else {

				// RESPONSAVEL PELO ANEXO

				byte[] buf = new byte[4096];

				String caminhoBase = Config.props.getProperty("diretorioAnexo");

				Multipart multi = (Multipart) content;

				for (int i = 0; i < multi.getCount(); i++) {

					String nomeAnexo = multi.getBodyPart(i).getFileName();

					if (nomeAnexo != null) {

						nomeAnexo = retiraAcento(nomeAnexo);

						status.append("\n\rAnexo:" + nomeAnexo);

						InputStream is = multi.getBodyPart(i).getInputStream();

						FileOutputStream fos = new FileOutputStream(caminhoBase
								+ nomeAnexo);

						int bytesRead;

						while ((bytesRead = is.read(buf)) != -1) {

							fos.write(buf, 0, bytesRead);

						}

						fos.close();

					}
				}
			}

		} catch (NoSuchProviderException nspe) {
			nspe.printStackTrace();

		} catch (MessagingException me) {
			me.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(1);
		}
	}

	public static String retiraAcento(String tratar) {

		try {

			if (tratar != null) {

				tratar = tratar.replaceAll("�", "A");
				tratar = tratar.replaceAll("�", "A");
				tratar = tratar.replaceAll("�", "A");
				tratar = tratar.replaceAll("�", "A");
				tratar = tratar.replaceAll("�", "A");

				tratar = tratar.replaceAll("�", "a");
				tratar = tratar.replaceAll("�", "a");
				tratar = tratar.replaceAll("�", "a");
				tratar = tratar.replaceAll("�", "a");
				tratar = tratar.replaceAll("�", "a");

				tratar = tratar.replaceAll("�", "E");
				tratar = tratar.replaceAll("�", "E");
				tratar = tratar.replaceAll("�", "E");
				tratar = tratar.replaceAll("�", "E");

				tratar = tratar.replaceAll("�", "e");
				tratar = tratar.replaceAll("�", "e");
				tratar = tratar.replaceAll("�", "e");
				tratar = tratar.replaceAll("�", "e");

				tratar = tratar.replaceAll("�", "I");
				tratar = tratar.replaceAll("�", "I");
				tratar = tratar.replaceAll("�", "I");
				tratar = tratar.replaceAll("�", "I");

				tratar = tratar.replaceAll("�", "i");
				tratar = tratar.replaceAll("�", "i");
				tratar = tratar.replaceAll("�", "i");
				tratar = tratar.replaceAll("�", "i");

				tratar = tratar.replaceAll("�", "O");
				tratar = tratar.replaceAll("�", "O");
				tratar = tratar.replaceAll("�", "O");
				tratar = tratar.replaceAll("�", "O");
				tratar = tratar.replaceAll("�", "O");

				tratar = tratar.replaceAll("�", "o");
				tratar = tratar.replaceAll("�", "o");
				tratar = tratar.replaceAll("�", "o");
				tratar = tratar.replaceAll("�", "o");
				tratar = tratar.replaceAll("�", "o");

				tratar = tratar.replaceAll("�", "U");
				tratar = tratar.replaceAll("�", "U");
				tratar = tratar.replaceAll("�", "U");
				tratar = tratar.replaceAll("�", "U");

				tratar = tratar.replaceAll("�", "u");
				tratar = tratar.replaceAll("�", "u");
				tratar = tratar.replaceAll("�", "u");
				tratar = tratar.replaceAll("�", "u");

				tratar = tratar.replaceAll("�", "C");

				tratar = tratar.replaceAll("�", "c");

				tratar = tratar.replaceAll("�", "y");
				tratar = tratar.replaceAll("�", "y");
				tratar = tratar.replaceAll("�", "Y");

				tratar = tratar.replaceAll("�", "n");

				tratar = tratar.replaceAll("�", "N");

				tratar = tratar.replaceAll("!", "");
				tratar = tratar.replaceAll("[?]", "");
				tratar = tratar.replaceAll("=", "");

			}

		}

		catch (Exception e) {

			status.append("\n\rError: " + e);

		}

		return tratar;

	}

}