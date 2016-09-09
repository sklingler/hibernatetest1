package hibertest;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sean.hibertest.entity.Country;
import com.sean.hibertest.entity.CountryMany;
import com.sean.hibertest.entity.CountryMask;

public class CacheQueryDemo {

	private static void displayCountries(List<Country> countries){
		countries.forEach(c -> System.out.println("Country: " + c));
	}
	
	// Try and simulate what is happening in services with the dto mapping, just using prints
	// to cause extra queries.
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml").
				addAnnotatedClass(Country.class).addAnnotatedClass(CountryMask.class).
				addAnnotatedClass(CountryMany.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		System.out.println("get a country by id");
		Country aCountry = session.get(Country.class, 1);
		
		System.out.println("querying countries");
		List<Country> countries = session.createQuery("from Country where id in (2,3)").getResultList();
		System.out.println("displaying countries");
		displayCountries(countries);
		System.out.println("show countries again");
		displayCountries(countries);
		
		session.getTransaction().commit();

		session = factory.getCurrentSession();
		session.beginTransaction();
		
		System.out.println("querying countries");
		countries = session.createQuery("from Country where id in (2,3)").getResultList();
		System.out.println("displaying countries");
		displayCountries(countries);
		System.out.println("show countries again");
		displayCountries(countries);
}

}
