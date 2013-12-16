package br.com.synchro.testes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import br.com.synchro.mail.utils.Config;

public class ConfigTest {
	@Test
	public void testVariavelProperties() {
		InputStream in = this.getClass().getResourceAsStream("mail.properties");
		Properties props = new Properties();
		try {

			props.load(in);
			new Config(props);
			Assert.assertEquals("webmail.synchro.com.br",
					Config.props.getProperty("servidorLeitura"));
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

}
