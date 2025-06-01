 Hospital Management System


Overview

  This is a web-based Hospital Management System built using Spring Boot, Thymeleaf, and MongoDB.
It enables the management of doctors, patients, medicines, and appointments with a clean interface and CRUD functionality.


Technologies Used

  - Spring Boot - Backend framework for building REST APIs and web controllers.
  - Thymeleaf - Templating engine for rendering dynamic HTML pages.
  - MongoDB - NoSQL database for storing doctors, patients, medicines, and appointments.
  - Bootstrap 5 - For responsive and consistent UI styling.

Modules

1. Patient Module
  - Add Patient: Form to input name, age, gender, contact number, and address.
  - View Patients: Table listing all patients with options to edit, delete, view details, and add medicine.
  - Edit Patient: Modify patient details.
  - Patient Details Page: Displays patient info along with their medicines and appointments.

2. Doctor Module
  - Add Doctor: Form for name and specialization.
  - View Doctors: Table showing doctors with options to delete or view.
  - Book Appointment: Select patient and date/time to assign appointment to a doctor.
  - Doctor Details Page: View doctor’s appointments and linked patients.

3. Medicine Module
  - Add Medicine: For a selected patient, a form is provided to input name, dosage, description, expiry date, and select a doctor.
  - Delete Medicine: Option to remove medicines linked to a patient.

4. Appointment Module
  - Book Appointment: Via doctor page.
  - View/Delete Appointments: Available on both patient and doctor detail pages.



Application Structure

  - controller package: Handles HTTP requests and routes them to views or services.
  - model package: Contains domain classes like Patient, Doctor, Medicine, Appointment. 
  - repository package: Interfaces for MongoDB CRUD operations.
  - service package: Business logic for each entity.
  - templates/: Thymeleaf HTML templates (add, view, edit pages for all modules).
  - static/: Contains custom CSS (style.css).
  


Features

  - Responsive UI using Bootstrap.
  - Dynamic content rendered using Thymeleaf.
  - Bi-directional association: Medicines link patients to doctors.
  - Form validation using Spring's BindingResult.
  - Confirmation dialogs for delete operations.

Future Enhancements

  - Add Spring Security for login/logout and role-based access (Doctor/Patient/Admin).
  - Export patient/doctor details as PDF.
  - Add pagination and search filters.
  - Dashboard for analytics and summary.

How to Run

  1. Clone the repository.
  2. Make sure MongoDB is running.
  3. Run the application via your IDE or command: mvn spring-boot:run
  4. Navigate to http://localhost:8080

Conclusion

  The Hospital Management System successfully streamlines the core functionalities of a healthcare facility by providing a simple, efficient,
and user-friendly platform for managing patients, doctors, medicines, and appointments. Built using Spring Boot, Thymeleaf, and MongoDB,
the system demonstrates a clean architectural structure and ensures maintainability and scalability for future enhancements. This project
lays a strong foundation for developing more advanced healthcare solutions with added features like authentication, reporting, and analytics.



Developer Info
  - Author: Aruneshwaran
  - Tools: IntelliJ, MongoDB Compass, Postman (for testing REST APIs)
  - Application Live link : https://hospital-management-mini-2.onrender.com
Note :
   Once you click the link it may take 1 or 2 mins to load the application so wait patiently.
  	Login info:-
  	admin:
  		username = doctor
  		password = doctor
