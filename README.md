
# N11 - Talenthub Bootcamp Final Project

## Overview
This project involves the development of two distinct services: a User Review Service and a Restaurant Definition Service. The User Review Service enables users to register, submit reviews for restaurants, and receive personalized restaurant recommendations based on their location and preferences. On the other hand, the Restaurant Definition Service focuses on managing restaurant data, allowing for the addition, update, and deletion of restaurant information. Both services aim to provide efficient and user-friendly solutions for handling user reviews and restaurant definitions, contributing to an enhanced dining experience for users.

## Overview of Application Architecture

The schematic representation of the application architecture is provided below. This visual illustrates the fundamental components and relationships within the application.

![Logo](https://i.ibb.co/93W5Vqg/design.png)


## Distrubited Systems

1. **Gateway-Service**

API Gateway serves as a central entry point for all incoming requests to the system. It logs both incoming requests and outgoing responses, sending them to a queue for further processing. Additionally, it facilitates request routing and load balancing. I've incorporated additional functionality for logging purposes, leveraging Spring Cloud Gateway.

2. **Logging Service**

The Logging Service monitors the log queue, capturing incoming messages and persisting them into a MongoDB database. This queue employs a retry mechanism, attempting to process messages multiple times. In case the processing fails after several attempts, the service routes the messages to a dead letter queue for further analysis or manual intervention. This ensures robustness in handling log data and provides a safety net for messages that couldn't be processed successfully initially.

3. **Config Server**

The Config Server is responsible for centralizing configuration data for distributed system applications. It maintains all configuration information and serves it to other systems upon request. By centralizing configuration management, this service ensures consistency and enables easy updates across the system. It simplifies the process of managing configuration parameters for various components, enhancing maintainability and scalability of the overall system.

4. **Service Registry**

The Service Registry acts as a centralized directory where microservices within the system can register themselves. It provides a dynamic and up-to-date catalog of available services and their locations, facilitating service discovery and communication between different components of the system. By maintaining an accurate and real-time registry of services, it enables seamless scalability, load balancing, and fault tolerance within the distributed system architecture.

5. **Review Service**

The Review Service is responsible for managing user reviews for restaurants. It interacts with both the user service and the restaurant service via RESTful requests and maintains its own PostgreSQL database. Whenever a review is submitted, it adds an entry to the score update queue, enabling asynchronous updating of the average scores for restaurants. This service ensures efficient handling of user feedback, updating restaurant ratings in real-time without impacting user experience.

6. **Recommendation Service**

The Recommendation Service provides users with three restaurant recommendations based on their current location within a 10 km radius and the ratings of the restaurants. It gathers the user's location and sends a request to the restaurant database using SolrJ to retrieve three restaurant recommendations. It applies the query efficiently on Solr, prioritizing response speed to ensure a seamless user experience. This service focuses on delivering fast and relevant restaurant suggestions to users, enhancing their satisfaction with the recommendation process.

7. **Restaurant Service**

The Restaurant Service manages restaurant definitions within the system. When saving restaurant data, alongside necessary information, latitude and longitude values are also expected in this service. It supports operations such as creating, deleting, updating, and listing restaurants. Restaurant data is stored in Apache Solr, and queries are directed to Solr for efficient retrieval and manipulation. This service ensures comprehensive management of restaurant entities while leveraging the power of Solr for fast and effective data operations.

8. **User Service**

The User Service is responsible for managing user entities within the system. It allows users to be created, deleted, updated, and listed. Additionally, users have addresses associated with them, which include latitude and longitude information. This allows the Recommendation Service to provide location-based recommendations to users. The User Service ensures comprehensive user management and facilitates the provision of personalized recommendations based on user locations.

9. **Zipkin**

All services are integrated with Zipkin for tracing purposes. Zipkin serves as a centralized system for collecting and correlating timing data across distributed services. By connecting to Zipkin, each service can contribute to distributed tracing, allowing me to monitor and analyze the flow of requests throughout the entire system. This integration enhances observability and facilitates troubleshooting and optimization efforts across the distributed architecture.



## API Reference
All services listed below has their own Swagger documentation with detailed explanation of service api's. But with **service-registry**, endpoints will be shown by each other as below.

### USER-SERVICE ```/api/v1/users ```

| Uri | Method     | Description                |
| :-------- | :------- | :------------------------- |
| `/` | `POST` | Creates new user. |
| `/{id}` | `GET` | Gets user by provided id. |
| `/` | `GET` | Gets all users |
| `/{id}` | `PATCH` | Updates user info. |
| `/deactivate/{id}` | `PATCH` | Deactivate user. (Soft Delete) |
| `/{id}` | `DELETE` | Deletes user. (Hard Delete) |


### ADDRESS-SERVICE ```/api/v1/addresses ```

| Uri | Method     | Description                |
| :-------- | :------- | :------------------------- |
| `/` | `POST` | Creates new address. |
| `/{id}` | `GET` | Gets address by provided id. |
| `/with-userId/{userId}` | `GET` | Gets addresses by provided user id. |
| `/` | `GET` | Gets all addresses |
| `/{id}` | `PATCH` | Updates address info. |
| `/deactivate/{id}` | `PATCH` | Deactivate address. (Soft Delete) |
| `/{id}` | `DELETE` | Deletes address. (Hard Delete) |


### RESTAURANT-SERVICE ```/api/v1/restaurants ```

| Uri | Method     | Description                |
| :-------- | :------- | :------------------------- |
| `/` | `POST` | Creates new restaurant. |
| `/{id}` | `GET` | Gets restaurant by provided id. |
| `/` | `GET` | Gets all restaurants. |
| `/{id}` | `PATCH` | Updates restaurant info. |
| `/deactivate/{id}` | `PATCH` | Deactivate restaurant. (Soft Delete) |
| `/{id}` | `DELETE` | Deletes restaurant. (Hard Delete) |

### REVIEW-SERVICE ```/api/v1/reviews ```

| Uri | Method     | Description                |
| :-------- | :------- | :------------------------- |
| `/` | `POST` | Creates new review. |
| `/{id}` | `GET` | Gets review by provided id. |
| `/` | `GET` | Gets all reviews. |
| `/with-userId/{userId}` | `GET` | Gets all reviews by provided user id. |
| `/with-restaurantId/{restaurantId}` | `GET` | Gets all reviews by provided restaurant id. |
| `/{id}` | `PATCH` | Updates review info. |
| `/{id}` | `DELETE` | Deletes review. (Hard Delete) |

### RECOMMENDATION-SERVICE ```/api/v1/recommendation ```

| Uri | Method     | Description                |
| :-------- | :------- | :------------------------- |
| `/{userId}` | `GET` | Gets 3 restaurant recommendations by provided user id. |


## Run Locally

Clone the project

```bash
  git clone https://github.com/mehmet-akif-tanisik/n11-talenthub-backend-bootcamp-final-case
```

### For Backend Services:
- You should go to **n11-talenthub-final-project-be/** to run docker compose file.

```bash
  docker-compose up
```


- Open **n11-talenthub-final-project-be** folder in your preferred IDE. Go to terminal and run command below for all services in the project. 

```bash
  mvn clean install
```
- Then First thing first Config Server should be up and running, and we are ready to run other services one by one.

### For FrontEnd:

- Open **n11-talenthub-final-project-fe** folder in your preferred IDE or Editor. Go to terminal.
```bash
  npm run dev
```

It is as easy as explained above. Now we are ready to see front end.


## Screenshots

![App Screenshot1](https://i.ibb.co/416Y0QL/1.png)
![App Screenshot2](https://i.ibb.co/qnvpqvK/5.png)
![App Screenshot3](https://i.ibb.co/qMfT8kQ/6.png)
![App Screenshot4](https://i.ibb.co/n3dXCh5/8.png)
![App Screenshot5](https://i.ibb.co/G7z198S/9.png)
![App Screenshot6](https://i.ibb.co/MNMmwZZ/11.png)

[//]: # (![App Screenshot7]&#40;https://i.ibb.co/47YgNcT/12.png&#41;)
![App Screenshot8](https://i.ibb.co/VWVSZPb/13.png)
![App Screenshot9](https://i.ibb.co/QjrRRsR/14.png)
![App Screenshot10](https://i.ibb.co/XCFc1cJ/16.png)

## Contact

### Mehmet Akif Tanisik

<a href="https://github.com/mehmet-akif-tanisik" target="_blank">
<img  src=https://img.shields.io/badge/github-%2324292e.svg?&style=for-the-badge&logo=github&logoColor=white alt=github style="margin-bottom: 20px;" />
</a>
<a href = "mailto:matnsk@outlook.com?subject = Feedback&body = Message">
<img src=https://img.shields.io/badge/send-email-email?&style=for-the-badge&logo=microsoftoutlook&color=CD5C5C alt=microsoftoutlook style="margin-bottom: 20px; margin-left:20px" />
</a>
<a href="https://linkedin.com/in/mehmet-akif-tanisik" target="_blank">
<img src=https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white alt=linkedin style="margin-bottom: 20px; margin-left:20px" />
</a>  
<a href="https://twitter.com/makiftanisik" target="_blank">
<img src=https://img.shields.io/badge/twitter-%2300acee.svg?&style=for-the-badge&logo=twitter&logoColor=white alt=twitter style="margin-bottom: 20px; margin-left:20px" />
</a>

<br />

## Talenthub Bootcamp - N11 & Patika
