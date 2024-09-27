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

## ScreenShot
![Screenshot 2024-09-27 214053](https://github.com/user-attachments/assets/89d38516-e980-4098-87af-93fd807e82fd)
![Screenshot 2024-09-27 214034](https://github.com/user-attachments/assets/2f6868f7-3f8e-4b03-a1a6-5865d46044c5)
![Screenshot 2024-09-27 214118](https://github.com/user-attachments/assets/9db786e8-9b8b-4e9f-a3b4-ce8d921ed0f9)
![Screenshot 2024-09-27 214131](https://github.com/user-attachments/assets/b1874c49-c35c-48a3-8554-69d0c9be2cfb)
![Screenshot 2024-09-27 214155](https://github.com/user-attachments/assets/82642ed7-708f-4976-927e-04b1c16517c2)
![Screenshot 2024-09-27 214323](https://github.com/user-attachments/assets/16cb71c5-6dff-4dfc-9101-ea212ffbb6ae)
![Screenshot 2024-09-27 214347](https://github.com/user-attachments/assets/5d6e539a-4d37-4ad1-8c69-d63e90cce371)
![Screenshot 2024-09-27 214404](https://github.com/user-attachments/assets/528d1812-06fb-4d85-9505-6cbac0c486ca)
![Screenshot 2024-09-27 214416](https://github.com/user-attachments/assets/20adcdcf-cf47-4afc-8d82-4b75dc514bb8)
![Screenshot 2024-09-27 214625](https://github.com/user-attachments/assets/1ab7d7be-8a31-4c64-8fad-c2bc23c79e34)


## Author
- [Rishi Raj]
