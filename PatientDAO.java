package HospitalManagementSystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PatientDAO {
    private SessionFactory sessionFactory;

    public PatientDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addPatient(Patient patient) {
    	Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(patient);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            throw e;
        }
    }

    public List<Patient> getAllPatients() {
    	Session session = sessionFactory.openSession();
        List<Patient> patients = null;
        try {
            session.beginTransaction();
            patients = session.createQuery("from Patient", Patient.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            throw e;
        }
        return patients;
    }

    public Patient getPatientById(int id) {
    	Session session = sessionFactory.openSession();
        Patient patient = null;
        try {
            session.beginTransaction();
            patient = session.get(Patient.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            throw e;
        }
        return patient;
    }
}
