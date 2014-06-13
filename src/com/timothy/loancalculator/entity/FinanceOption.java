package com.timothy.loancalculator.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="financeoption")
public class FinanceOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3015339217578313254L;
	private Integer id;
	private String name;
	private Institution institution;
	private InterestRate interestRate;
	
	@Id
	@SequenceGenerator(name="my_Seq",sequenceName="HIB_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="my_Seq")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name = "institution_id_fk")
	public Institution getInstitution() {
		return institution;
	}
	
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
	
	@OneToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name = "interest_rate_id_fk")
	public InterestRate getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(InterestRate interestRate) {
		this.interestRate = interestRate;
	}
	@Override
	public String toString() {
		return "FinanceOption [id=" + id + ", name=" + name + " ]";
	}
	
	
	
	
	
	
}
