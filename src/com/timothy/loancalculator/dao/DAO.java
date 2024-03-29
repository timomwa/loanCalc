package com.timothy.loancalculator.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DAO {
	private static final Logger log = Logger.getAnonymousLogger();
	private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	private static final SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	
	protected DAO() {
	}

	public static Session getSession() {
		Session session = (Session) DAO.session.get();
		if (session == null) {
			session = sessionFactory.openSession();
			DAO.session.set(session);
			getSession().setFlushMode(FlushMode.AUTO);
			
		}
		return session;
	}

	protected void begin() {
		getSession().beginTransaction();
	}

	protected void commit() {
		getSession().getTransaction().commit();
	}

	protected void rollback() {

		try {
			getSession().getTransaction().rollback();
		} catch (HibernateException e) {
			log.log(Level.WARNING, "Cannot rollback", e);
		}
		try {
			getSession().close();
		} catch (HibernateException e) {
			log.log(Level.WARNING, "Cannot close", e);
		}
		DAO.session.set(null);
	}

	public static void close() {
		getSession().close();
		DAO.session.set(null);
	}

	
}