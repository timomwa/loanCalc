package com.timothy.loancalculator.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "interestrate")
public class InterestRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744243508490750706L;

	@Id
	@SequenceGenerator(name = "my_Seqld", sequenceName = "HIB_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "my_Seqld")
	private Integer id;
	
	
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "rate",unique = true)
	private double rate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "InterestRate [id=" + id + ", name=" + name + ", rate=" + rate
				+ "]";
	}


	

}
