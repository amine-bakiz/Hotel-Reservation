🏨 Hotel Reservation System – Full Stack Web Application
This project is a full-stack hotel reservation platform designed to streamline the process of room booking and management. It is built using Spring Boot for the backend and Angular with TypeScript for the frontend, implementing secure authentication with JWT and data persistence with MySQL.

🔧 Technologies Used
Frontend:

Angular

TypeScript

HTML/CSS

Angular HTTP Client & Reactive Forms

Backend:

Spring Boot (REST APIs)

Spring Security (with JWT Authentication)

JPA/Hibernate

MySQL

🔐 Authentication & Authorization
JWT-based authentication system for secure login.

Role-based access:

Admin: manage rooms, view all reservations.

Customer: view available rooms, book rooms, manage bookings.

🌐 Key Features
🧑‍💼 Admin Features:
Add, update, delete rooms.

View all reservations.

Manage user accounts.

🙋 Customer Features:
Register/Login with JWT.

Search and filter available rooms by date.

Book a room for selected dates.

View and manage personal reservations.

🗃 Database
MySQL used to store user accounts, room information, and reservation records.

Relational mapping handled via Spring Data JPA.

🛠 Project Structure
bash
Copier
Modifier
HotelProject/
├── hotel/                # Spring Boot backend
│   └── src/main/java/com/hotel/...
└── hotelreservation/     # Angular frontend
    └── src/app/...
