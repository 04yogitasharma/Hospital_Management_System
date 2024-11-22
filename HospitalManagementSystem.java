package HospitalManagementSystem;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Doctor.class)
                .addAnnotatedClass(Appointment.class)
                .addAnnotatedClass(PatientDAO.class)
                .addAnnotatedClass(AppointmentDAO.class)
                .addAnnotatedClass(DoctorDAO.class)
                .buildSessionFactory();

        Scanner scanner = new Scanner(System.in);
        PatientDAO patientDAO = new PatientDAO(sessionFactory);
        AppointmentDAO appointmentDAO = new AppointmentDAO(sessionFactory);
        DoctorDAO doctorDAO=new DoctorDAO(sessionFactory);
        boolean running=true;
        
        while(running) {
        System.out.println("1. Add Patient");
        System.out.println("2. View Patients");
        System.out.println("3. View Doctors");
        System.out.println("4. Book Appointment");
        System.out.println("5. View Appointments");
        System.out.println("6. Update Appointment Status");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter Patient Name: ");
                String name = scanner.next();
                System.out.print("Enter Patient Age: ");
                int age = scanner.nextInt();
                System.out.print("Enter Patient Gender: ");
                String gender = scanner.next();
                System.out.print("Enter Patient Email: ");
                String email = scanner.next();
                Patient patient = new Patient(name, age, gender, email);
                patientDAO.addPatient(patient);
                break;

            case 2:
                List<Patient> patients = patientDAO.getAllPatients();
                System.out.println("Patients: ");
    			System.out.println("+------------+-------------------+------------+--------------+------------------------------+");
    			System.out.println("| Patient Id | Name              |  Age       | Gender       | Email                        |");
    			System.out.println("+------------+-------------------+------------+--------------+------------------------------+");
    			for (Patient patient1 : patients) {
    				System.out.printf("| %-10s | %-17s | %-10s | %-12s | %-28s |\n",patient1.getId(),patient1.getName(),patient1.getAge(),patient1.getGender
    						(),patient1.getEmail());
    				System.out.println("+------------+-------------------+------------+--------------+------------------------------+");

				}
                break;

               
            case 3:
            	List<Doctor> doctors=doctorDAO.getAllDoctors();
//    		
//                for (Doctor d : doctors) {
//                	System.out.println();
				System.out.println("Doctors: ");
    			System.out.println("+------------+-------------------+--------------------+");
    			System.out.println("| Doctor Id  | Name              | Specialization     |");
    			System.out.println("+------------+-------------------+--------------------+");	 
//            	doctors.forEach(d ->System.out.println("ID: "+d.getId()+", Name: "+d.getName()+", Specialization: "+d.getSpecialization()));  
            	
            	for(Doctor doctor : doctors){
            	System.out.printf("| %-10s | %-17s | %-18s |\n",doctor.getId(),doctor.getName(),doctor.getSpecialization());
            	System.out.println("+------------+-------------------+--------------------+");
//                 System.out.println();
            	}
            	break;
            	
            case 4:
                System.out.println("Enter Patient ID: ");
                int patientId = scanner.nextInt();
                System.out.println("Enter Doctor ID: ");
                int doctorId = scanner.nextInt();
                // Simulate doctor lookup (you should implement a DoctorDAO to get doctor by id)
                Doctor doctor=doctorDAO.getDoctorById(doctorId);
                Patient patient1 = patientDAO.getPatientById(patientId);
                System.out.println("Enter Appointment Date (yyyy-MM-dd HH:mm): ");
                String dateStr = scanner.next();
                Date date=java.sql.Date.valueOf(dateStr);
                Appointment appointment = new Appointment(patient1, doctor, new java.util.Date(), "pending");
                appointmentDAO.bookAppointment(appointment);
                try {
                	EmailSender.sendEmail(patient1.getEmail(),"Appointment Confirmation","Your appointment with Dr. "+ doctor.getName()+" has been confirmed for "+dateStr);
                }
                catch(Exception e) {
                	System.out.println("Error sending email : "+e.getMessage());
                }
                System.out.println("Appointment booked successfully.");
                break;

            case 5:
                System.out.println("Enter Patient ID to view appointments: ");
                int patientIdForAppointments = scanner.nextInt();
                Patient patientForAppointments = patientDAO.getPatientById(patientIdForAppointments);
                List<Appointment> appointments = appointmentDAO.getAppointmentsByPatient(patientForAppointments);
                System.out.println("Appointments: ");
                System.out.println("+-------------+------------+------------+--------------------------+----------+");
                System.out.println("| Appointment | Patient ID | Doctor ID | Appointment Date          | Status   |");
                System.out.println("+-------------+------------+------------+--------------------------+----------+");
                for (Appointment ap : appointments) {
                	System.out.printf("| %-11d | %-10d | %-10d | %-24s | %-8s |\n", ap.getId(), patientIdForAppointments, ap.getDoctor().getId(), ap.getAppointmentDate(), ap.getStatus());
                    System.out.println("+-------------+------------+------------+--------------------------+----------+");
				}
                break;

            case 6:
                System.out.println("Enter Appointment ID: ");
                int appointmentId = scanner.nextInt();
                System.out.println("Enter new status (pending, completed, canceled): ");
                String status = scanner.next();
                appointmentDAO.updateAppointmentStatus(appointmentId, status);
                System.out.println("Appointment status updated.");
                break;

            case 7:
            	System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM");
            	running=false;
            	break;
            	
            default:
                System.out.println("Invalid choice.");
        }
        }
        scanner.close();
        sessionFactory.close();
    }
}
