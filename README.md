# Doctor Appointment Booking System

## Overview
This Java-based GUI application enables users to book doctor appointments and view appointment details. The system interacts with a MySQL database to retrieve and display doctor details, including photos, availability, and other relevant information. The project is developed using Java Swing for the user interface and JDBC for database connectivity.

## Features
- **Doctor Search and Appointment Booking:**
  - Search doctors by specialty, name, or registration number.
  - View doctor details including name, photo, education, and available timings.
  
- **Appointment Navigation:**
  - Scroll through different doctor profiles and available time slots.
  - Navigate between appointments using "Left" and "Right" buttons.
  
- **Image Integration:**
  - Displays the doctor's circular photo and other appointment details.
  - Handles image rendering using `Graphics2D` and `BufferedImage`.

- **Database Interaction:**
  - Retrieves doctor information from a MySQL database.
  - Displays appointment details dynamically based on real-time data from the database.

## Technologies Used
- **Java:** Core programming language for developing the application.
- **Java Swing:** Used for building the graphical user interface (GUI).
- **JDBC (Java Database Connectivity):** For managing connections and interactions with the MySQL database.
- **MySQL Database:** Stores doctor information such as names, photos, education, and availability.
- **Image Processing:** Displays and handles doctor images within the GUI using Java's `BufferedImage` and `Graphics2D`.

## How to Run
1. **Clone the Repository:**
   ```
   git clone https://github.com/your-repo/doctor-appointment-system.git
   ```

2. **Set Up the Database:**
   - Create a MySQL database and import the provided SQL script (`database.sql`).
   - Update the database connection parameters (e.g., URL, username, password) in the code.

3. **Run the Application:**
   - Open the project in any Java IDE (e.g., IntelliJ IDEA, Eclipse).
   - Compile and run the `First.java` file.

## Dependencies
- Java Development Kit (JDK) 8 or higher.
- MySQL Server.
- Java Swing and AWT libraries (included in JDK).
- JDBC driver for MySQL.

## Future Improvements
- Implement filtering by doctor specialties or ratings.
- Add user authentication for patients to track their appointment history.
- Enable email or SMS notifications for upcoming appointments.

## Author
- [Your Name]
