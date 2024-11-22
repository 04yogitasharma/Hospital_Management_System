package HospitalManagementSystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AppointmentDAO {
    private SessionFactory sessionFactory;

    public AppointmentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void bookAppointment(Appointment appointment) {
    	Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(appointment);
            session.getTransaction().commit();
            System.out.println("Appointment booked successfully.");
        } 
        catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    
 // 2. Retrieve all appointments
    public List<Appointment> getAllAppointments() {
        Session session = sessionFactory.openSession();
        List<Appointment> appointments = null;
        try {
            session.beginTransaction();
            appointments = session.createQuery("from Appointment", Appointment.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return appointments;
    }

 // 3. Retrieve appointments by Patient
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        Session session = sessionFactory.openSession();
        List<Appointment> appointments = null;
        try {
            session.beginTransaction();
            appointments = session.createQuery("from Appointment where patient = :patient", Appointment.class)
                    .setParameter("patient", patient)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return appointments;
    }

    // 4. Retrieve appointments by Doctor
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        Session session = sessionFactory.openSession();
        List<Appointment> appointments = null;
        try {
            session.beginTransaction();
            appointments = session.createQuery("from Appointment where doctor = :doctor", Appointment.class)
                    .setParameter("doctor", doctor)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return appointments;
    }
    
 // 5. Retrieve a specific appointment by ID
    public Appointment getAppointmentById(int appointmentId) {
        Session session = sessionFactory.openSession();
        Appointment appointment = null;
        try {
            session.beginTransaction();
            appointment = session.get(Appointment.class, appointmentId);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return appointment;
    }
    
 // 6. Update appointment status
    public void updateAppointmentStatus(int appointmentId, String newStatus) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Appointment appointment = session.get(Appointment.class, appointmentId);
            if (appointment != null) {
                appointment.setStatus(newStatus);
                session.update(appointment);
                System.out.println("Appointment status updated successfully.");
            } else {
                System.out.println("Appointment with ID " + appointmentId + " not found.");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
