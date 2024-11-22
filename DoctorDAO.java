package HospitalManagementSystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class DoctorDAO {
    private SessionFactory sessionFactory;

    public DoctorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addDoctor(Doctor doctor) {
    	 Session session = sessionFactory.openSession();
         try {
             session.beginTransaction();
             session.save(doctor);
             session.getTransaction().commit();
         } catch (Exception e) {
             if (session.getTransaction() != null) session.getTransaction().rollback();
             throw e;
         }
    }

    public List<Doctor> getAllDoctors() {
    	Session session = sessionFactory.openSession();
        List<Doctor> doctors = null;
        try {
            session.beginTransaction(); // Start transaction
            
            System.out.println();
            doctors = session.createQuery("from Doctor", Doctor.class).getResultList();
//            System.out.println("+------------+-------------------+--------------------+");
//            Doctor id=doctors.get(0);
//            Doctor name=doctors.get(1);
//            Doctor specialization=doctors.get(2);
            

            
            session.getTransaction().commit(); // Commit transaction
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback(); // Rollback if error
            throw e;
        }
        return doctors;
    }
    
    public Doctor getDoctorById(int doctorId) {
    	Session session = sessionFactory.openSession();
        Doctor doctor = null;
        try {
            session.beginTransaction();
            doctor = session.get(Doctor.class, doctorId);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            throw e;
        }
        return doctor;

    }
}

