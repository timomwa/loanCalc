package com.timothy.loancalculator.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.timothy.loancalculator.entity.FinanceOption;
import com.timothy.loancalculator.entity.Institution;
import com.timothy.loancalculator.entity.InterestRate;

@Remote
public interface LoanCalculatorBeanRemote {
	public FinanceOption getFinanceOption(int id);
	public List<InterestRate> getAllInterestRates(int starting_row, int list_size);
	public List<FinanceOption> getAllFinanceOptions(int starting_row, int list_size);
	public List<Institution> getAllInstitutions(int starting_row, int list_size);
	public int saveFinancialOption(FinanceOption financialOption);
	public Institution getInstitution(int id);
	public int saveInstitution(Institution institution);
	public InterestRate getInterestRate(int id);
	public InterestRate saveInterestRate(InterestRate interestRate);
	public void deleteFinanceOption(FinanceOption finance_option);
	public double calculateInstallment(FinanceOption financeOption,double principle_amt);
	//public Institution getInstitutionFromFinanceOption(int financial_option_id);
	public InterestRate getInterestRateByRate(double interest_rate);
	public FinanceOption getFinanceOptionByInterestRateIdAndInstitutionId(
			int interest_rate, int institution_id);
	public Institution getInstitutionByName(String name);
}
