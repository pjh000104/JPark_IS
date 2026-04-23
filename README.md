# JPark_IS

This repository contains the "Local Cuisine App" project, a full-stack web application developed to explore and demonstrate key backend web development concepts. The project includes a research paper that investigates software architecture, database systems, and caching strategies.
The application allows users to discover local foods from different regions, read and write reviews, and find nearby restaurants that serve the selected cuisine.

This work was completed as a senior independent study project for the Computer Science major at The College of Wooster.

## Key Features

- **Explore by Region**: Browse a curated list of geographic regions to discover their culinary specialties.
- **Cuisine Details**: View detailed descriptions for each local dish.
- **User Reviews**: Read and submit ratings and comments for cuisines.
- **Restaurant Finder**: Seamlessly search for nearby restaurants on Google Maps for any selected cuisine.
- **Performance Optimized**: The backend leverages caching with Redis to ensure fast response times for frequently accessed data.

## Project Structure

The repository is organized into the following main directories:

-   `Code/localcuisine`: Contains the Java Spring Boot backend application.
-   `Code/frontend`: Contains the Next.js frontend application.
-   `Writing`: Contains the research paper.
-   `Annotatedbib`: The annotated bibliography for the research component.

## Tech Stack

-   **Backend**: Java 17, Spring Boot, Spring Data JPA, Maven
-   **Frontend**: Next.js, React, TypeScript, Tailwind CSS
-   **Database**: PostgreSQL (managed via Supabase)
-   **Caching**: Redis

## Getting Started

To run this project locally, you will need to set up both the backend and frontend services.

### Prerequisites

-   Java 17 or later
-   Maven
-   Node.js and npm (or yarn)
-   A running PostgreSQL instance
-   A running Redis instance

### Backend Setup

1.  **Configure Database and Redis**:
    Update the `src/main/resources/application.properties` file with your PostgreSQL and Redis connection details.

    ```properties
    # PostgreSQL Configuration
    spring.datasource.url=jdbc:postgresql://<your-db-host>:<port>/<db-name>
    spring.datasource.username=<your-username>
    spring.datasource.password=<your-password>
    spring.jpa.hibernate.ddl-auto=update

    # Redis Configuration
    spring.data.redis.host=<your-redis-host>
    spring.data.redis.port=<your-redis-port>
    ```

2.  **Navigate to the backend directory**:
    ```bash
    cd Code/localcuisine
    ```

3.  **Run the application**:
    ```bash
    ./mvnw spring-boot:run
    ```
    The backend server will start on `http://localhost:8080`.

### Frontend Setup

1.  **Navigate to the frontend directory**:
    ```bash
    cd Code/frontend
    ```

2.  **Install dependencies**:
    ```bash
    npm install
    ```

3.  **Set up environment variables**:
    Create a `.env.local` file in the `Code/frontend` directory and add the backend API URL:
    ```
    NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
    ```

4.  **Run the development server**:
    ```bash
    npm run dev
    ```
    The frontend will be available at `http://localhost:3000`.
