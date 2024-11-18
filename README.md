# A Simple Todo List App demonstrating TestContainers

This project demonstrates how to use Testcontainers with Spring Boot to simplify testing and ensure compatibility between your application and different database systems.

## Tech Stack

- **Client**: Represents the user interface, which in this case is a browser.
- **Server**: Represents the server-side application, which includes Spring Boot, Spring Data JPA, and PostgreSQL.
- **Testcontainers**: Represents the Testcontainers environment, which includes a PostgreSQL container for testing.

## How it works?

The client sends a request to the server. The server processes the request using Spring Boot, Spring Data JPA, and PostgreSQL.
If the server is running in a Testcontainers environment, Testcontainers sets up a PostgreSQL container for testing.

<img width="1083" alt="image" src="https://github.com/user-attachments/assets/27ea7cba-f413-45bc-b1a6-6f0e506ded38">



## Built With
- Spring Boot - The web framework used
- Maven - Dependency Management
- Testcontainers - Containerization for testing

## Getting Started

## Using Docker Compose

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

![image](https://github.com/user-attachments/assets/38bcb37c-1b24-4be6-99ac-3ba2c7f569a3)

Now, you should be able to add a list of tasks.

Let's verify if Postgres is functional or not

Open Docker Dashboard > Select Postgres container > Click "EXEC" and open the container terminal.

```
/ # psql -U postgres
psql (16.5)
Type "help" for help.

postgres=# \l
                                                      List of databases
   Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | ICU Locale | ICU Rules |   Access privileges   
-----------+----------+----------+-----------------+------------+------------+------------+-----------+-----------------------
 postgres  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | 
 template0 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres          +
           |          |          |                 |            |            |            |           | postgres=CTc/postgres
 template1 | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres          +
           |          |          |                 |            |            |            |           | postgres=CTc/postgres
(3 rows) 
         
postgres=# 
postgres=# 
postgres=# 
postgres=# \dt
                 List of relations
 Schema |         Name          | Type  |  Owner   
--------+-----------------------+-------+----------
 public | flyway_schema_history | table | postgres
 public | todos                 | table | postgres
(2 rows)

postgres=# SELECT * from todos;
 id | title | completed | order_number 
----+-------+-----------+--------------
(0 rows)

postgres=# SELECT * from todos;
                  id                  |     title     | completed | order_number 
--------------------------------------+---------------+-----------+--------------
 fd1d8e2a-a950-4d3d-a780-47773f8dd14e | Watch Netflix | f         |            1
(1 row)
```

### Step 4. Bring down the compose services

Open Docker Dashboard > Select the Compose service > Click "Delete".

<img width="1005" alt="image" src="https://github.com/user-attachments/assets/66704208-b7b2-41dc-aab6-8a2a85894031">

## Why Testcontainers?

Docker Compose is a great development tool  that focuses on Application packaging, deployment, and delivery.
But when it comes to testing, you might need a better tool.

- Limited support for parallel test execution.
- Requires manual orchestration and teardown of containers.
- Lacks features to dynamically adjust test environments per test case.

Testcontainers simplifyies test environment management.

- Allows tests to spin up lightweight, isolated containers dynamically.
- Supports parallel test execution with isolated environments for each test case.
- Automatically manages container lifecycle (start, stop, cleanup).
- Provides integrations with testing frameworks like JUnit, PyTest, etc.
- Supports fine-grained control over environment configuration, such as databases, message brokers, or even entire application stacks.

While Docker Compose shines in development environments where you need to run the entire stack for local testing or debugging, it falls short in test automation scenarios that require parallelism and dynamic provisioning of environments. For such use cases, Testcontainers is a more suitable choice, providing the flexibility and isolation needed for robust, automated testing.

## Using Testcontainers Open Source

To replace Docker Compose with Testcontainers, you can manage the db and server containers programmatically, seed the database, and ensure proper integration testing. Below is a solution to spin up your db and server services using Testcontainers. Later, you'll see how to seed the database, test the backend/frontend interactions, and clean up afterward.



Clone this repository or download and unzip it, if you haven't done earlier.

```
cd todo-app-testcontainers
./mvnw spring-boot:test-run                                    
```

<img width="1452" alt="image" src="https://github.com/user-attachments/assets/014206aa-46cd-4809-8850-5869e62acb0b">



## Using Testcontainers Desktop App

Install [Testcontainers Desktop app](https://testcontainers.com/desktop?utm_medium=event&utm_source=2023-springone&utm_content=raffle-repo). It is free to try and takes less than 5 minutes!


<img width="448" alt="image" src="https://github.com/user-attachments/assets/adae2cd6-3880-4ddd-bc3c-fbd14f75bfcf">

Choose "Tail logs" to see the following result:

```

Success. You can now start the database server using:

    pg_ctl -D /var/lib/postgresql/data -l logfile start

waiting for server to start....2024-11-16 12:45:49.781 UTC [41] LOG:  starting PostgreSQL 16.5 on aarch64-unknown-linux-musl, compiled by gcc (Alpine 13.2.1_git20240309) 13.2.1 20240309, 64-bit
2024-11-16 12:45:49.781 UTC [41] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
2024-11-16 12:45:49.782 UTC [44] LOG:  database system was shut down at 2024-11-16 12:45:49 UTC
2024-11-16 12:45:49.784 UTC [41] LOG:  database system is ready to accept connections
 done
server started
CREATE DATABASE


/usr/local/bin/docker-entrypoint.sh: ignoring /docker-entrypoint-initdb.d/*

waiting for server to shut down....2024-11-16 12:45:49.944 UTC [41] LOG:  received fast shutdown request
2024-11-16 12:45:49.944 UTC [41] LOG:  aborting any active transactions
2024-11-16 12:45:49.946 UTC [41] LOG:  background worker "logical replication launcher" (PID 47) exited with exit code 1
2024-11-16 12:45:49.946 UTC [42] LOG:  shutting down
2024-11-16 12:45:49.946 UTC [42] LOG:  checkpoint starting: shutdown immediate
2024-11-16 12:45:49.958 UTC [42] LOG:  checkpoint complete: wrote 924 buffers (5.6%); 0 WAL file(s) added, 0 removed, 0 recycled; write=0.012 s, sync=0.001 s, total=0.012 s; sync files=0, longest=0.000 s, average=0.000 s; distance=4267 kB, estimate=4267 kB; lsn=0/191AB10, redo lsn=0/191AB10
2024-11-16 12:45:49.961 UTC [41] LOG:  database system is shut down
 done
server stopped

PostgreSQL init process complete; ready for start up.

2024-11-16 12:45:50.060 UTC [1] LOG:  starting PostgreSQL 16.5 on aarch64-unknown-linux-musl, compiled by gcc (Alpine 13.2.1_git20240309) 13.2.1 20240309, 64-bit
2024-11-16 12:45:50.060 UTC [1] LOG:  listening on IPv4 address "0.0.0.0", port 5432
2024-11-16 12:45:50.060 UTC [1] LOG:  listening on IPv6 address "::", port 5432
2024-11-16 12:45:50.061 UTC [1] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
2024-11-16 12:45:50.062 UTC [57] LOG:  database system was shut down at 2024-11-16 12:45:49 UTC
2024-11-16 12:45:50.064 UTC [1] LOG:  database system is ready to accept connections
2024-11-16 12:50:50.069 UTC [55] LOG:  checkpoint starting: time
2024-11-16 12:51:00.228 UTC [55] LOG:  checkpoint complete: wrote 101 buffers (0.6%); 0 WAL file(s) added, 0 removed, 0 recycled; write=10.155 s, sync=0.001 s, total=10.159 s; sync files=0, longest=0.000 s, average=0.000 s; distance=467 kB, estimate=467 kB; lsn=0/198F840, redo lsn=0/198F808
```

## Accessing the frontend

![image](https://github.com/user-attachments/assets/431b62c4-c184-44da-a24d-1b0c2fd7f10c)

Let's verify if Postgres is functional or not

Open Docker Dashboard > Select Postgres container > Click "EXEC" and open the container terminal.

```
/ # psql -U testuser -d testdb
psql (16.5)
Type "help" for help.

testdb=# 
```

### List all DBs

```
testdb=# \l
                                                      List of databases
   Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | ICU Locale | ICU Rules |   Access privileges   
-----------+----------+----------+-----------------+------------+------------+------------+-----------+-----------------------
 postgres  | testuser | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | 
 template0 | testuser | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/testuser          +
           |          |          |                 |            |            |            |           | testuser=CTc/testuser
 template1 | testuser | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/testuser          +
           |          |          |                 |            |            |            |           | testuser=CTc/testuser
 testdb    | testuser | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | 
(4 rows)

testdb=#
```

### Switch to testdb

```
testdb=# \c testdb
You are now connected to database "testdb" as user "testuser".
testdb=#
```

### List the tables

```
testdb=# \dt
                 List of relations
 Schema |         Name          | Type  |  Owner   
--------+-----------------------+-------+----------
 public | flyway_schema_history | table | testuser
 public | todos                 | table | testuser
(2 rows)

testdb=#
```

### Table Information


```
testdb=# \d todos
                          Table "public.todos"
    Column    |          Type          | Collation | Nullable | Default 
--------------+------------------------+-----------+----------+---------
 id           | character varying(100) |           | not null | 
 title        | character varying(200) |           | not null | 
 completed    | boolean                |           |          | false
 order_number | integer                |           |          | 
Indexes:
    "todos_pkey" PRIMARY KEY, btree (id)

testdb=#
```

## List the items

```
testdb=# SELECT * from todos;
 id | title | completed | order_number 
----+-------+-----------+--------------
(0 rows)
```

Let's add items in the frontend 

![image](https://github.com/user-attachments/assets/30d20142-c067-4b20-94d3-5805bb19f7b5)


### Verifying:

```
testdb=# SELECT * from todos;
                  id                  |     title     | completed | order_number 
--------------------------------------+---------------+-----------+--------------
 3dd20ce7-e045-4caf-93c1-d6b23876bb09 | Buy Grocery   | f         |            1
 6aa7a992-a042-43a6-be45-ea0d4b328f5f | Watch Netflix | f         |            2
(2 rows)
```

## Implementing the frontend change

Locate the `todomvc-common.css` file (src/main/resources/public/css/vendor/todomvc-common.css) and change the hex value for the background color of the body (around line 25): 



<img width="1270" alt="image" src="https://github.com/user-attachments/assets/c693347e-ce91-4470-91f0-69719a6f1481">


Let's change it to blue "#a69cff". Don't forget to save this file.

<img width="980" alt="image" src="https://github.com/user-attachments/assets/0e2c0eb6-44ac-4c90-8548-558f7b5f9162">




## Using TestContainers Cloud

3. Clone this repository or download and unzip it. 
4. Run application locally letting Spring Boot and Testcontainers set up a database for it: `./mvnw spring-boot:test-run`
5. Open the application in the browser: [link](http://localhost:8080/?http://localhost:8080/todos)
6. Look at the beautiful and working TODO application
7. Optional: Check out the `ContainersConfig` class to see how elegant the Spring Boot and Testcontainers integration is now.



