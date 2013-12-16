package br.com.synchro.mail.utils;

/**
 * @author Alceu Moreira
 * @since 03/2013
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static Properties props = new Properties();

	public Config() {
		InputStream in = this.getClass().getResourceAsStream("mail.properties");
		if (in == null)
			throw new IllegalArgumentException(
					"Nao Foi encontrado o arquivo Mail");
		try {
			props.load(in);

		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}

	public Config(Properties propsMail) {
		props = propsMail;
	}

	public static void main(String[] args) {
		new Config();
	}

}
