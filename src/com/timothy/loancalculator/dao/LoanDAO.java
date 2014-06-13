package com.timothy.loancalculator.dao;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.timothy.loancalculator.ejb.LoanCalculatorBeanRemote;
import com.timothy.loancalculator.entity.FinanceOption;
import com.timothy.loancalculator.entity.Institution;
import com.timothy.loancalculator.entity.InterestRate;



public class LoanDAO extends BaseDAO{
	
	public static String FAIL_JSON = "{ \"response\" : \"{\"type\" : \"1\", \"message\" : \"Something went wrong :(\"}, \"}";
	private  LoanCalculatorBeanRemote loan_calc = null;
	private static LoanDAO this_dao = null;
	private DecimalFormat df = null;
	private Logger logger = Logger.getLogger(LoanDAO.class);
	public static LoanDAO getInstance() {
		if(this_dao==null)
			this_dao = new LoanDAO();
		return this_dao;
	}
	
	protected LoanDAO(){
		init();
	}

	private void init() {
		try {
			loan_calc = (LoanCalculatorBeanRemote) ctx
					.lookup("LoanCalculatorBean/remote");
			DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
			symbols.setGroupingSeparator(',');
			df = new DecimalFormat("###,###.##",symbols);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	public FinanceOption getFinanceOption(int id){
		return loan_calc.getFinanceOption(id);
	}
	public Institution getInstitution(int id){
		return loan_calc.getInstitution(id);
	}
	public InterestRate getInterestRate(int id){
		return loan_calc.getInterestRate(id);
	}
	
	public String saveFinanceOption(FinanceOption finance_option){
		String json_resp = FAIL_JSON;
		try{
			int id = loan_calc.saveFinancialOption(finance_option);
			json_resp = "{ \"response\" : {\"type\" : 0, \"message\" : {\"id\": "+id+", \"finance_option_id\" : "+finance_option.getId()+", \"institution\": \""+finance_option.getName()+"\"} } }";
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return json_resp;
	}
	public void saveInstitution(Institution institution){
		loan_calc.saveInstitution(institution);
	}
	public InterestRate saveInterestRate(InterestRate interest_rate){
		
		interest_rate =  loan_calc.saveInterestRate(interest_rate);
		return interest_rate;
	}
	public List<FinanceOption> getAllFinanceOptions(int starting_row, int list_size){
		return loan_calc.getAllFinanceOptions(starting_row, list_size);
	}
	public List<Institution> getAllInstitutions(int starting_row, int list_size){
		return loan_calc.getAllInstitutions(starting_row, list_size);
	}
	public List<InterestRate> getAllInterestRates(int starting_row, int list_size){
		return loan_calc.getAllInterestRates(starting_row, list_size);
	}
	
	
	
	public String saveInstitution(String name){
		Institution inst = loan_calc.getInstitutionByName(name);
		boolean exists = false;
		if(inst!=null){
			exists = true;
		}else{
			inst = new Institution();
		}
		inst.setName(name);
		String json_resp = FAIL_JSON;
		try{
			int id = loan_calc.saveInstitution(inst);
			if(inst.getId()!=null)
				json_resp = "{ \"response\" : {\"type\" : 0, \"message\" :  {\"id\": "+inst.getId()+", \"institution\": \""+name+"\"} } }";
			else
				json_resp = "{ \"response\" : {\"type\" : 0, \"message\" :  {\"id\": "+id+", \"institution\": \""+name+"\"} } }";
		}catch(Exception e){
			e.printStackTrace();
		}
		return json_resp;
	}

	public String calculateMonthlyPayment(double principle_amount,
			double period_months, int pagination_start_at, int pagination_max_results) {
		
		String json_string = FAIL_JSON;
		try {
			List<Institution> insts = getInstance().getAllInstitutions(pagination_start_at, pagination_max_results);
			JSONArray premiums = new JSONArray();
			
			if (insts != null)
				for (Institution i : insts) {
					List<FinanceOption> opts = i.getFinance_options();
					if (opts != null)
						for (FinanceOption op : opts) {
							JSONObject premium = new JSONObject();
							premium.put("finance_option_id", op.getId());
							double monthly_payment = getMonthlyPayment(Double.valueOf(principle_amount),
									op.getInterestRate().getRate(), Double.valueOf(period_months));
							
							premium.put("monthly_replayment",df.format(monthly_payment));
							premium.put("number_of_months", Double.valueOf(period_months));
							premiums.put(premium);
						}
				}
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("premiums", premiums);
			json_string = jsonObj.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json_string;
	}
	
	
	
	public String getAllInstitutions_(int pagination_start_at, int pagination_max_results) {
		
		String json_string = "{ \"response\" : \"type\" : \"error\", \"message\" : \"Something went wrong :( \" }, }";
		try {
			List<Institution> insts = getInstance().getAllInstitutions(pagination_start_at, pagination_max_results);
			JSONArray banks = new JSONArray();
			
			if (insts != null)
				for (Institution i : insts) {
					JSONObject bank = new JSONObject();
					bank.put("id", i.getId());
					bank.put("bankName", i.getName());
					banks.put(bank);
				}
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("bankList", banks);
			json_string = "("+jsonObj.toString()+")";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json_string;
	}

	
	protected double getMonthlyPayment(double loanAmount, double interest,
			double number_months) {
		double monthlyInterest = interest / 1200;
		double monthlyRepayment = loanAmount * monthlyInterest
				/ (1 - (Math.pow(1 + monthlyInterest, -number_months)));
		
		return monthlyRepayment;
	}

	public String delteFinanceOption(int financial_option_id) {
		FinanceOption opt = getFinanceOption(financial_option_id);
		
		loan_calc.deleteFinanceOption(opt);
		String json_resp = "{ \"response\" : {\"type\" : 0, \"message\" : {\"id\": "+financial_option_id+", \"msg\": \"Deleted finance option successfully\"} } }";
		return json_resp;
	}

	

	public InterestRate getInterestRateByRate(double interest_rate) {
		return loan_calc.getInterestRateByRate(interest_rate);
	}

	public FinanceOption getFinanceOptionByInterestRateIdAndInstitutionId(
			int interest_rate, int institution_id) {
		return loan_calc.getFinanceOptionByInterestRateIdAndInstitutionId(interest_rate,institution_id);
	}
	
	

}
