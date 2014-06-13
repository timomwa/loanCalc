package com.timothy.loancalculator.action;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;

import com.timothy.loancalculator.dao.LoanDAO;
import com.timothy.loancalculator.entity.FinanceOption;
import com.timothy.loancalculator.entity.Institution;
import com.timothy.loancalculator.entity.InterestRate;

public class LoansActionBean extends BaseActionBean {

	private static final String VIEW = "/WEB-INF/jsp/loanfinder.jsp";
    private int pagination_start_at = 0;
    private int pagination_max_results = 100;
    private Logger logger = Logger.getLogger(LoansActionBean.class);
	private LoanDAO loan_dao = LoanDAO.getInstance();
	private int period_months = -1;
	private double principle_amount;
	private String institutionName;
	private String finance_option_name;
	private double interest_rate;
	private int institution_id;
	private int financial_option_id;
	private int finance_option_id;

	public List<Institution> getInstitutions() {
		List<Institution> insts = loan_dao.getAllInstitutions(pagination_start_at, pagination_max_results); 

		if (insts != null)
			for (Institution i : insts) {
				List<FinanceOption> opts = i.getFinance_options();
				if (opts != null)
					for (FinanceOption op : opts) {
						System.out.println(op.getName());
					}
			}
		return insts;
	}

	@DefaultHandler
	public Resolution list() {
		return new ForwardResolution(VIEW);
	}
	public Resolution getAllInstitutions(){
		return new StreamingResolution("application/json", new StringReader(loan_dao.getAllInstitutions_(pagination_start_at, pagination_max_results)));
	}

	public Resolution calculateRate() {
		String json_string = loan_dao.calculateMonthlyPayment(principle_amount, period_months,pagination_start_at, pagination_max_results);
		return new StreamingResolution("application/json", new StringReader(json_string));
	}
	
	public Resolution getFinancialOption(){
		String resp = "";
		FinanceOption opt = loan_dao.getFinanceOption(financial_option_id);
		Institution inst = opt.getInstitution();
		InterestRate ir = opt.getInterestRate();
		resp = "{ \"response\" : {\"type\" : 0, \"message\" : {\"id\": "+financial_option_id+", \"name\": \""+opt.getName()+"\", \"rate\": \""+ir.getRate()+"\",  \"institution_id\": \""+inst.getId()+"\",  \"institutionName\": \""+inst.getName()+"\"} } }";
		return new StreamingResolution("application/json", new StringReader(resp));
	}
	
	public Resolution saveFinanceOption(){
		
		Institution institution = null;
		InterestRate isr = null;
		FinanceOption finance_option =null;
		String resp = "";
		
		try{
			
		    	finance_option = loan_dao.getFinanceOption(finance_option_id);
		    	isr = loan_dao.getInterestRateByRate(interest_rate);
		    	if(finance_option==null){
		    		finance_option = new FinanceOption();
		    	}
		    	finance_option.setName(finance_option_name);
		    	
		    	if(isr==null){
		    		isr = new InterestRate();
		    		isr.setName(interest_rate+"%");
			    	isr.setRate(interest_rate);
			    	isr = loan_dao.saveInterestRate(isr);
		    	}
		    	
		    	finance_option.setInterestRate(isr);
		    	
		    	institution = loan_dao.getInstitution(institution_id);
		    	List<FinanceOption> fo = institution.getFinance_options();
		    	fo.add(finance_option);
		    	institution.setFinance_options(fo);
		    	
		    	loan_dao.saveInstitution(institution);
		    	resp = loan_dao.saveFinanceOption(finance_option);
		    	
		    
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return new StreamingResolution("application/json", new StringReader(resp));
	}
	
	public Resolution deleteFinancialOption(){
		return new StreamingResolution("application/json", new StringReader(loan_dao.delteFinanceOption(financial_option_id)));
	}
	public Resolution saveInstitution(){
		return new StreamingResolution("application/json", new StringReader(loan_dao.saveInstitution(institutionName)));
	}

	public int getPeriod_months() {
		return period_months;
	}

	public void setPeriod_months(int period_months) {
		this.period_months = period_months;
	}

	public double getPrinciple_amount() {
		return principle_amount;
	}

	public void setPrinciple_amount(double principle_amount) {
		this.principle_amount = principle_amount;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getFinance_option_name() {
		return finance_option_name;
	}

	public void setFinance_option_name(String finance_option_name) {
		this.finance_option_name = finance_option_name;
	}

	public double getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(double interest_rate) {
		this.interest_rate = interest_rate;
	}

	public int getInstitution_id() {
		return institution_id;
	}

	public void setInstitution_id(int institution_id) {
		this.institution_id = institution_id;
	}

	public int getPagination_start_at() {
		return pagination_start_at;
	}

	public void setPagination_start_at(int pagination_start_at) {
		this.pagination_start_at = pagination_start_at;
	}

	public int getPagination_max_results() {
		return pagination_max_results;
	}

	public void setPagination_max_results(int pagination_max_results) {
		this.pagination_max_results = pagination_max_results;
	}

	public int getFinancial_option_id() {
		return financial_option_id;
	}

	public void setFinancial_option_id(int financial_option_id) {
		this.financial_option_id = financial_option_id;
	}

	public int getFinance_option_id() {
		return finance_option_id;
	}

	public void setFinance_option_id(int finance_option_id) {
		this.finance_option_id = finance_option_id;
	}
	
	
	
}
