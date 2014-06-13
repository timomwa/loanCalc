package com.timothy.loancalculator.ejb;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.timothy.loancalculator.entity.FinanceOption;
import com.timothy.loancalculator.entity.Institution;
import com.timothy.loancalculator.entity.InterestRate;

@Stateless
public class LoanCalculatorBean implements LoanCalculatorBeanRemote {
	
	Logger logger = Logger.getLogger(LoanCalculatorBean.class);

	public LoanCalculatorBean() {
	}
	
	
	@PersistenceContext(unitName = "EjbComponentPU")
	private EntityManager entityManager;
	@Override
	public FinanceOption getFinanceOption(int id) {
		Query qry = entityManager.createQuery("from FinanceOption where id = :id");
		qry.setParameter("id", id);
		FinanceOption fopt = null;
		try{
			fopt = (FinanceOption) qry.getSingleResult();
		}catch(NoResultException e){
			logger.warn(e.getMessage() + "  Couldn't find FinanceOption with id ["+id+"]");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return fopt;
	}

	@Override
	public int saveFinancialOption(FinanceOption financialOption) {
		if(financialOption.getId()!=null){
			
			entityManager.merge(financialOption);
		}else{
			entityManager.persist(financialOption);
		}
		entityManager.flush();
		return financialOption.getId();
	}

	@Override
	public Institution getInstitution(int id) {
		Query qry = entityManager.createQuery("from Institution where id = :id");
		qry.setParameter("id", id);
		 Institution inst = null;
		 
		try{
			 inst = (Institution) qry.getSingleResult();
		}catch(NoResultException e){
				logger.warn(e.getMessage() + "  Couldn't find Institution with id ["+id+"]");
		}catch(Exception e){
				logger.error(e.getMessage(),e);
		}
		 return inst;
	}

	@Override
	public int saveInstitution(Institution institution) {
		if(institution.getId()!=null){//if not a new bank...
			entityManager.merge(institution);
		}else{
			entityManager.persist(institution);
		}
		entityManager.flush();
		
		return institution.getId();
		
	}

	@Override
	public InterestRate getInterestRate(int id) {
		Query qry = entityManager.createQuery("from InterestRate where id = :id");
		qry.setParameter("id", id);
		InterestRate isr = null;
		try{
			isr = (InterestRate) qry.getSingleResult();
		}catch(NoResultException nre){
			logger.error(nre.getMessage() + "\n\n\nCould not get Interest rate of id ["+id+"] \n\n",nre);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return isr;
	}

	@Override
	public double calculateInstallment(FinanceOption financeOption, double principle_amt) {
		return principle_amt;
	}

	@Override
	public InterestRate saveInterestRate(InterestRate interestRate) {
		try{
			if(interestRate.getId()==null){
				final Session session = (Session) entityManager.getDelegate();
				session.save(interestRate);
			}else{
					entityManager.merge(interestRate);
			}
		}catch(Exception e){
			logger.error(e.getMessage()+"Couldn't persist intereStRate interestRate = "+interestRate,e);
		}
		try{
			entityManager.flush();
		}catch(Exception e){
			logger.error(e.getMessage()+"\n\nUnable to persist!!!\n\n",e);
		}
		return interestRate;
	}

	@Override
	public List<Institution> getAllInstitutions(int starting_row, int list_size) {
		Query qry = entityManager.createQuery("from Institution");
		qry.setFirstResult(starting_row);
		qry.setMaxResults(list_size);
		return (List<Institution>) qry.getResultList();
	}

	@Override
	public List<InterestRate> getAllInterestRates(int starting_row,
			int list_size) {
		Query qry = entityManager.createQuery("from InterestRate");
			qry.setFirstResult(starting_row);
			qry.setMaxResults(list_size);
			return (List<InterestRate>) qry.getResultList();
	}

	@Override
	public List<FinanceOption> getAllFinanceOptions(int starting_row,
			int list_size) {
		Query qry = entityManager.createQuery("from FinanceOption");
		qry.setFirstResult(starting_row);
		qry.setMaxResults(list_size);
		return (List<FinanceOption>) qry.getResultList();
	}

	@Override
	public void deleteFinanceOption(FinanceOption finance_option) {
		entityManager.remove(entityManager.contains(finance_option) ? finance_option : entityManager.merge(finance_option));
		entityManager.flush();
		
	}



	@Override
	public InterestRate getInterestRateByRate(double interest_rate) {
		Query qry = entityManager.createQuery("from InterestRate where rate = :rate");
		qry.setParameter("rate", interest_rate);
		InterestRate isr = null;
		try{
			isr = (InterestRate) qry.getSingleResult();
		}catch(NoResultException e){
			logger.warn(e.getMessage() + "  Couldn't find interest rate rate ["+interest_rate+"]");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return isr;
	}

	@Override
	public FinanceOption getFinanceOptionByInterestRateIdAndInstitutionId(
			int interest_rate_id, int institution_id) {
		Query qry = entityManager.createQuery("from FinanceOption where interest_rate_id_fk = :interest_rate_id_fk AND institution_id_fk = :institution_id_fk");
		qry.setParameter("interest_rate_id_fk", interest_rate_id);
		qry.setParameter("institution_id_fk", institution_id);
		FinanceOption fio = null;
		try{
			fio = (FinanceOption) qry.getSingleResult();
		}catch(NoResultException e){
			logger.warn("\n\n\n"+e.getMessage() + "  Couldn't find FinanceOption interest_rate_id=["+interest_rate_id+"], institution_id = ["+institution_id+"] \n\n\n");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return fio;
	}

	@Override
	public Institution getInstitutionByName(String name) {
		Query qry = entityManager.createQuery("from Institution where name = :name");
		qry.setParameter("name", name);
		Institution inst = null;
		try{
			inst = (Institution) qry.getSingleResult();
		}catch(NoResultException e){
			logger.warn("\n\n\n"+e.getMessage() + "  Couldn't find Institution with name "+name+" \n\n\n");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return inst;
	}
	
	

}
