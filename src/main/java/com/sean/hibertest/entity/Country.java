package com.sean.hibertest.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	private Set<CountryMany> dbManys = new HashSet<CountryMany>(0);
	
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

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dbCountry")
	public Set<CountryMany> getDbManys() {
		return dbManys;
	}

	public void setDbManys(Set<CountryMany> dbManys) {
		this.dbManys = dbManys;
	}

	@Override
	public String toString() {
		String sMask = null;
		CountryMask cMask = getDbCountryMask();
		if(cMask != null)
			sMask = cMask.getName();
		Set<CountryMany> manys = getDbManys();
		String sMany = null;
		if(manys != null && !manys.isEmpty()){
			CountryMany m = manys.iterator().next();
			sMany = m.getName();
		}
		return "Country [id=" + id + ", name=" + name + " mask=" + sMask + " many=" + sMany + "]";
	}
}
