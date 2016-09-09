package com.sean.hibertest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "countrymany")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CountryMany {
	private int id;

	private String name;

	private Country dbCountry;
	
	public CountryMany() {
	}
	
	public CountryMany(String name){
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "countryid", nullable = false)
	public Country getDbCountry() {
		return dbCountry;
	}

	public void setDbCountry(Country country) {
		this.dbCountry = country;
	}

	@Override
	public String toString() {
		Country rCountry = getDbCountry();
		String sCountry = null;
		if(rCountry != null)
			sCountry = rCountry.getName();
		return "CountryMask [id=" + id + ", name=" + name + " country=" + sCountry + "]";
	}

}
