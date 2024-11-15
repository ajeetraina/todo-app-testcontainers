# A Simple Todo List App demonstrating TestContainers

This project demonstrates how to use Testcontainers with Spring Boot to simplify testing and ensure compatibility between your application and different database systems.

## Tech Stack

- Client: Represents the user interface, which in this case is a browser.
- Server: Represents the server-side application, which includes Spring Boot, Spring Data JPA, and PostgreSQL.
- Testcontainers: Represents the Testcontainers environment, which includes a PostgreSQL container for testing.

The client sends a request to the server. The server processes the request using Spring Boot, Spring Data JPA, and PostgreSQL.
If the server is running in a Testcontainers environment, Testcontainers sets up a PostgreSQL container for testing.

![image](https://github.com/user-attachments/assets/61e09194-a30a-4737-8ae9-bca862a120f9)


## Built With
- Spring Boot - The web framework used
- Maven - Dependency Management
- Testcontainers - Containerization for testing

## Getting Started

## Spinning up the complete application

### Step 1. Clone the repository

```
git clone https://github.com/ajeetraina/todo-app-testcontainers
cd todo-app-testcontainers
```

### Step 2.Bring up services 

```
docker compose up -d --build
```

<img width="1466" alt="image" src="https://github.com/user-attachments/assets/62c42fe5-2dab-4b70-8812-5f54f4a6624f">


### Step 3. Accessing the app

Open [http://localhost:8080](http://localhost:8080) to acces the frontend app

Add the following backend URL - [http://localhost:8080/todos](http://localhost:8080/todos) to the frontend.

![image](https://github.com/user-attachments/assets/3330b4fd-1de7-4b08-8a96-011d052cc7e2)





1. Install [Testcontainers Desktop app](https://testcontainers.com/desktop?utm_medium=event&utm_source=2023-springone&utm_content=raffle-repo). It is free to try and takes less than 5 minutes!
2. Configure Testcontainers Desktop to run with Testcontainers Cloud (select the correct menu item)
3. Clone this repository or download and unzip it. 
4. Run application locally letting Spring Boot and Testcontainers set up a database for it: `./mvnw spring-boot:test-run`
5. Open the application in the browser: [link](http://localhost:8080/?http://localhost:8080/todos)
6. Look at the beautiful and working TODO application
7. Optional: Check out the `ContainersConfig` class to see how elegant the Spring Boot and Testcontainers integration is now.



