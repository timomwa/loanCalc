package com.timothy.loancalculator.initializers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Query;

import com.timothy.loancalculator.dao.DAO;
import com.timothy.loancalculator.entity.FinanceOption;
import com.timothy.loancalculator.entity.Institution;
import com.timothy.loancalculator.entity.InterestRate;

public class InitializerClass extends DAO {

	public static void main(String[] args) {
		
		//createSchema();
		
		
		InitializerClass ic = new InitializerClass();

		for (int x = 1; x <= 3; x++) {
			// create institutions
			Institution inst = new Institution();
			if (x == 1)
				inst.setName("Standard Chartered");
			if (x == 2)
				inst.setName("BarclaysBank");
			if (x == 3)
				inst.setName("Equity Bank");
			ic.create(inst);

			// Create interest rates
			inst = ic.getInstitution(x);
			double rate = 0.0d;
			if (x == 1)
				rate = 18;
			if (x == 2)
				rate = 15;
			if (x == 3)
				rate = 16;
			InterestRate interest_rate = new InterestRate();
			interest_rate.setName(inst.getName() + "'s " + rate + "%");
			interest_rate.setRate(rate);
			
			
			interest_rate = (InterestRate) ic.create(interest_rate);

			// create finance options
			//InterestRate isr = ic.get(x);

			FinanceOption fo = new FinanceOption();
			fo.setInterestRate(interest_rate);
			String name = inst.getName() + "'s Option for " + interest_rate.getRate()
					+ " %";
			fo.setName(name);
			
			fo = (FinanceOption) ic.create(fo);
			
			inst.getFinance_options().add(fo);
			ic.create(inst);
			
		}

		ic.close();

	}

	

	private Institution getInstitution(int id) {
		begin();
		Query qry = getSession().createQuery("from Institution where id = :id");
		qry.setParameter("id", id);
		List<Institution> irate = (List<Institution>) qry.list();
		Institution inst = null;
		for (Institution inst_ : irate)
			inst = inst_;
		return inst;
	}

	public Object create(Object obj) {
		begin();
		Object obj_ = null;
	
		if (obj.getClass().getCanonicalName()
				.equals("com.timothy.loancalculator.entity.Institution")) {
			Institution inst = (Institution) obj;
			getSession().saveOrUpdate(inst);
			obj_ = inst;

		}
		if (obj.getClass().getCanonicalName()
				.equals("com.timothy.loancalculator.entity.InterestRate")) {
			InterestRate ir = (InterestRate) obj;
			getSession().saveOrUpdate(ir);
			obj_ = ir;

		}
		if (obj.getClass().getCanonicalName()
				.equals("com.timothy.loancalculator.entity.FinanceOption")) {
			FinanceOption fo = (FinanceOption) obj;
			getSession().saveOrUpdate(fo);
			obj_ = fo;

		}
		commit();
		return obj_;

	}

	/*
	 * public User create(String username, String password) throws Exception {
	 * try { begin(); User user = new User(username, password);
	 * getSession().save(user); commit(); return user; } catch
	 * (HibernateException e) { throw new Exception("Could not create user " +
	 * username, e); } }
	 */
}