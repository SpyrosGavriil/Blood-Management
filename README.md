# Blood Management System

A web-based application for managing blood donations and blood banks efficiently. This system provides an interface for users to view their donation history and available blood banks, while admins can manage blood banks, donors, donation records, and register new admins.

## 💻 Tech Stack

### Back-end

- **Java (Spring Boot)** – Powers the RESTful APIs and manages the core functionality of the Blood Management System.
- **MySQL** – A relational database used to store information about users, admins, donors, donation records, and blood banks.
- **JPA (Java Persistence API)** – Simplifies database interactions using ORM (Object-Relational Mapping) through `JpaRepository` for CRUD operations.
- **Maven** – Handles dependency management and automates project builds.
- **Spring Security** - Provides security features, including authentication and authorization for the system.
- **JWT (JSON Web Tokens)** - Ensures secure user authentication and session management through token-based authentication.

### Front-end

- **HTML5** - Structures the web application, including forms and tables to manage blood donation-related tasks.
- **CSS3** - Styles the web interface to make it visually appealing and user-friendly..
- **JavaScript (Vanilla JS)** - Adds dynamic interactivity on the client side for features like form submissions and data visualization.

### Tools & Utilities

- **Postman** – For testing and verifying API endpoints during development.

## Features

- **Users:**
  - View donation history and total donations.
  - Browse available blood banks.
- **Admins:**
  - Create, update, and delete blood banks, donors, and donation records.
  - Register new admins and manage existing ones.
- **Security:**
  - JWT-based authentication and authorization.

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/SpyrosGavriil/Blood-Management.git
   cd Blood-Management
   ```

2. **Backend Setup:**

   - Install Java 17 or higher.
   - Install Maven.
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bloodmanagement
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```
   - Run the application:
     ```bash
     mvn spring-boot:run
     ```

3. **Database Setup:**

   - Create a MySQL database named `bloodmanagement`.
   - The database will be automatically seeded with data when the application starts.

4. **Access the Application:**
   - API Base URL: `http://localhost:8080`

## Usage

1. **Login:**

   - Use the `/api/auth/login` endpoint to authenticate.
   - Copy the JWT token from the response.

2. **Access APIs:**
   - Add the JWT token to the `Authorization` header as `Bearer <your_token>` for all requests.

## Contributing

Contributions are welcome! Follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/YourFeature`.
3. Commit your changes: `git commit -m 'Add YourFeature'`.
4. Push to the branch: `git push origin feature/YourFeature`.
5. Open a pull request.

## License

This project is licensed under the MIT License.

## Contact

For questions or feedback, feel free to reach out:

- **GitHub:** [SpyrosGavriil](https://github.com/SpyrosGavriil)
