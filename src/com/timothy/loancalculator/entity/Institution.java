package com.timothy.loancalculator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="institution")
public class Institution implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2995428410394596605L;


	@Id
	@SequenceGenerator(name="my_Seql",sequenceName="HIB_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="my_Seql")
	private Integer id;
	
	@Column(name="name", nullable=false, length=60, unique=true)
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name = "institution_id_fk")
	private List<FinanceOption> finance_options =  new ArrayList<FinanceOption>();
	
	
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
	
	
	public List<FinanceOption> getFinance_options() {
		return finance_options;
	}
	
	public void setFinance_options(List<FinanceOption> finance_options) {
		this.finance_options = finance_options;
	}
	@Override
	public String toString() {
		return "Institution [id=" + id + ", name=" + name
				+ ", finance_options=" + finance_options + "]";
	}
	
	
	
	

}
