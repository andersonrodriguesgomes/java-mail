package br.com.synchro.testes;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import br.com.synchro.mail.ReadMail;
import br.com.synchro.mail.utils.Config;

public class ReadMailTest {

	@Test
	public void executaLeituraEmailFunciyonal() {
		new Config();
		ReadMail executaTesteLeitura = new ReadMail();
		String status = executaTesteLeitura.executaLeituraEmail();

		if (status.contains("Conta de e-mail sem mensagens.")) {
			EnvioDeEmailTeste enviarMensagemTeste = new EnvioDeEmailTeste();
			enviarMensagemTeste.enviarMensagem();
		} else {
			Assert.assertTrue(new File(
					"z:/13130384534924000168550010000056271021202346-teste-seiko-3-forn-diferente.xml")
					.exists());
		}

	}

	@Test
	public void jogaAnexoEmPastaIndicadaNoProperties() {
		// TODO Falta realizar os testes de E-mail e anexo
		// TODO testar se o SAP irá realizar a leitura do XML
	}
}
