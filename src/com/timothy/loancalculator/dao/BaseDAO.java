package com.timothy.loancalculator.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class BaseDAO {

	/*private BaseDAO(){
	}*/
	
	BufferedReader brConsoleReader = null;
	Properties props;
	InitialContext ctx;
	 InputStream inputStream;
	{
		props = new Properties();
		try {
			 inputStream = this.getClass().getClassLoader().getResourceAsStream("jndi.properties");
			  props.load(inputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			/*props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
			props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
			props.setProperty("java.naming.provider.url", "localhost");*/
			ctx = new InitialContext(props);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
	}
	
}
