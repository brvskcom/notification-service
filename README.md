
# NOTIFICATION-SERVICE

This service allows sending notifications to users who have placed orders in the application. 

Types of notifications:
- Email (Java Mail Sender)
- SMS (Twilio)


## Technologies
- Java 17
- Spring Boot 3.1.4
- Spring Cloud 
- Docker
- PostgreSQL
- Kafka
- Java Mail Sender
- Twilio
## Features
#### Current:
- asynchronous communication with order-service via Kafka
- email notifications
- sms notifications
- saving notification history to the database
#### To-do
- newsletter emails

