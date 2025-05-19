# Investment Aggregator

A backend application designed to simplify the management and aggregation of financial investment data. Built with Java and Spring Boot, this system allows users to efficiently manage investment portfolios, register financial products, and generate insightful summaries of their investments.

## Features

- **User Management:** Secure registration and account management.
- **Investment Products:** Register and manage a variety of financial products.
- **Portfolio Management:** Create customized investment portfolios.
- **Data Aggregation:** Summarize and analyze investments by user and type.
- **RESTful API:** Well-documented and intuitive API endpoints for easy integration.

## Technologies Used

- Java
- Spring Boot
- MySQL
- Docker
- Maven

## Getting Started

### Prerequisites

- Java 11 or later
- Maven
- Docker
- Docker Compose

### Installation

1. **Clone the repository**:

```bash
git clone https://github.com/YwaoFugimoto/investment-aggregator.git
cd investment-aggregator
```

2. **Build the application**:
```bash
mvn clean install
```

3. **Launch using Docker Compose** 
```bash
docker compose up 
```
The application and databases services will now be running locally.

## API Documentation
The backend provides RESTful endpoints for interaction:
### Users: 

- ### `POST /users: Create a new user`

- ### `GET /users: Retrieve all users`

- ### `GET /users/{userId}: Retrieve specific user details`

- ### `PUT /users/{userId}: Update specific user details`

- ### `DELETE /users/{userId}: Remove a user`

### Financial accounts:

- ### `GET /users/{userId}/accounts: Retrieve all accounts of a user`

- ### `POST /users/{userId}/accounts: Create a new account for a user`

