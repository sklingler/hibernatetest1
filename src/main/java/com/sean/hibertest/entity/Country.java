package com.sean.hibertest.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country {
	private int id;

	private String name;

	private CountryMask dbCountryMask = null;
	
	public Country() {
		
	}
	
	public Country(String name){
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "dbCountry", optional = false)
//    @LazyToOne(LazyToOneOption.NO_PROXY)
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public CountryMask getDbCountryMask() {
		return dbCountryMask;
	}

	public void setDbCountryMask(CountryMask countryMask) {
		this.dbCountryMask = countryMask;
	}

	@Override
	public String toString() {
		String sMask = null;
		CountryMask cMask = getDbCountryMask();
		if(cMask != null)
			sMask = cMask.getName();
		return "Country [id=" + id + ", name=" + name + " mask=" + sMask + "]";
	}
}
