package hibertest;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sean.hibertest.entity.Student;

public class CreateStudentDemo {

	private static void testStudentReadback(SessionFactory factory, Student readbackStudent){
		System.out.println("reading back studenty by Id: " + readbackStudent.getId());
		Session session = factory.getCurrentSession();
		
		session.beginTransaction();
		Student readStudent = session.get(Student.class, readbackStudent.getId());
		session.getTransaction().commit();
		if(readStudent != null){
			System.out.println("did not find the Student: " + readStudent);			
		}
		else {
			System.out.println("did not find the Student");
		}
	}
	
	private static void testStudentQuery(SessionFactory factory){
		Session session = factory.getCurrentSession();
		try {			
			
			// start a transaction
			session.beginTransaction();
			
			// query students
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			
			// display the students
			displayStudents(theStudents);
			
			// query students: lastName='Doe'
			theStudents = session.createQuery("from Student s where s.lastName='Doe'").getResultList();
			
			// display the students
			System.out.println("\n\nStudents who have last name of Doe");
			displayStudents(theStudents);
			
			// query students: lastName='Doe' OR firstName='Daffy'
			theStudents =
					session.createQuery("from Student s where"
							+ " s.lastName='Doe' OR s.firstName='Daffy'").getResultList();
			
			System.out.println("\n\nStudents who have last name of Doe OR first name Daffy");
			displayStudents(theStudents);
			
			// query students where email LIKE '%gmail.com'
			theStudents = session.createQuery("from Student s where"
					+ " s.email LIKE '%gmail.com'").getResultList();

			System.out.println("\n\nStudents whose email ends with gmail.com");			
			displayStudents(theStudents);
			
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			//factory.close();
		}
	}
	
	private static void displayStudents(List<Student> students) {
		for (Student tempStudent : students) {
			System.out.println(tempStudent);
		}
	}


	private static void testUpdateFirstName(SessionFactory factory, String name, String newName){
		Session session = factory.getCurrentSession();
		
		/*
		 * The important thing to note here is that you do not have to save or persist the object.
		 * Just doing the commit will catch the changes we made
		 */
		session.beginTransaction();
		List<Student> students = session.createQuery("from Student s where"
				+ " s.firstName='" + name + "'").getResultList();
		students.forEach(s -> s.setFirstName(newName));
		session.getTransaction().commit();
	}
	
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();

		try {
			// create a student object
			System.out.println("Creating new student object...");
			Student tempStudent = new Student("Paul", "Wall", "paul@luv2code.com");

			// start a transaction
			session.beginTransaction();

			// save the student object
			System.out.println("Saving the student...");
			session.save(tempStudent);

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Creating 3 more student objects...");
			Student tempStudent1 = new Student("John", "Doe", "john@luv2code.com");
			Student tempStudent2 = new Student("Mary", "Public", "mary@luv2code.com");
			Student tempStudent3 = new Student("Bonita", "Applebum", "bonita@luv2code.com");
			
			// start a transaction
			System.out.println("starting next transaction");
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the students...");
			session.save(tempStudent1);
			session.save(tempStudent2);
			session.save(tempStudent3);
			
			// commit transaction
			session.getTransaction().commit();
			
			testStudentReadback(factory, tempStudent1);
			
			testStudentQuery(factory);
			
			testUpdateFirstName(factory, "John", "Jane");
			
			System.out.println("Done!");
		} finally {
			factory.close();
		}
	}

}
