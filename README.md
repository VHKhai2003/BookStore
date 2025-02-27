# Book Store Web Application

## 📖 Overview

The **BookStore** Web Application is a web-based platform that allows users to browse, purchase, and manage books online. 
It is built using **Spring Boot** with Thymeleaf for templating and **Spring Security** for authentication and authorization. 
The application uses **PostgreSQL** as its database.

## 🌍 URL
- **Production:** [https://my-bookstore-5def.onrender.com](https://my-bookstore-5def.onrender.com)
- **Accounts:** 
  
  | Role  |   | Username     | Password     |
  |-------|---|--------------|--------------|
  | user  |   | duykhiem123  | Khiem@123456 |
  | admin |   | huynhkhai123 | Khai@123456  |

## ✨ Features

- **🔐 User Authentication & Authorization**
  - Secure login and registration with Spring Security
  - Role-based access (Admin, User)
- **📚 Book Management**
  - Add, edit, and delete books (Admin only)
  - View book details and search, filter functionality
- **🛒 Shopping Cart**
  - Add books to cart
  - Checkout and order management
- **👤 User Management**
  - Admin can manage user accounts (view, update)
  - User profile
- **📊 Statistics**
  - Dashboard with analytics and insights
- **🗄 Database Integration**
  - Uses **PostgreSQL** for data storage
  - JPA for ORM (Object-Relational Mapping)
- **🎨 Thymeleaf Integration**
  - Dynamic and responsive UI

## 🛠 Technologies Used

- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Database**: PostgreSQL
- **Build Tool**: Maven

## ⚡ Prerequisites

To run this project, ensure you have the following installed:

- Java 17 or later
- Maven
- PostgreSQL

## 🚀 Installation & Setup

### 1️⃣ Clone the repository

```sh
git clone https://github.com/VHKhai2003/BookStore.git
cd bookstore
```

### 2️⃣ Configure Database

Update `application.properties` with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Using SQL scripts in `database` folder

### 3️⃣ Build and Run the Application

```sh
mvn clean install
mvn spring-boot:run
```

### 4️⃣ Access the Application

- Open [http://localhost:8080](http://localhost:8080) in your browser.


## 🤝 Contribution

If you would like to contribute, feel free to fork the repository and submit a pull request.

## 📧 Contact

For questions or support:

-   **Email**: vuonghuynhkhai4869@gmail.com


