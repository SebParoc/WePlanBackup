# WePlan

Group members: 

Ilaria Santangelo, Sebastian Augusto Paz Rocha

## Description

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

WePlan is a web application developed with Spring Framework which main focus is task organization. It provides the user with several customizable features such as, different types of tasks and events, importable/exportable timetable, and timers.

The user can also be friends with other users, have common tasks and share files through them.


## How to run it
```bash
 java -jar target/WePlan-0.0.1-SNAPSHOT.jar
```
To build and run this web application, [Tomcat](http://tomcat.apache.org) is needed. Tomcat is an application server from the Apache Software Foundation that executes Java servlets and renders Web pages that include Java Server Page coding

A mySQL database is required to run the project too. All we need is to create an empty database called "ppdatabase". We can also import the .sql file that is included in the repository.

After we set up Tomcat and the mySQL database, we should see the following message when running the application.
```bash
 Tomcat started on port(s): 8080 (http) with context path ''
```
And we are ready to go!

## How to use WePlan?
To get started, you must create an account, and submit some information about you. 

After you log in, you will be redirected to the User Area, there you can see a link to your profile, your tasks, timetable and friend request. In the left part you can find a nav bar where you can add friends, create tasks, and access the "Study mode". 

Study mode is simple and provides the user with customizable timers. So the user is not only able to organize their tasks, but also to have a personal space and be productive.

There are three types of tasks:
- The Simple task, which is individual and the user can save files there and set a deadline.
- The "Teams" task, which is shareable with other users, all the collaborators can see it and upload and download files.
- The Scheduled task, which has a start time and end time, but not the option to upload files.

The tasks are sorted and organized in the user area, being part of a timetable. The timetable (list of tasks) can be exported to CSV or imported from CSV.

In the Study mode the user can create custom timers, or go for something more traditional, like the Pomodoro timer, which is set by default.

The user can also add friends, and see them in the profile page. There they have the option to visit their profile and see the shared tasks, or delete them.


## Implementation

We developed WePlan with the following technologies:
- Java, Maven, JUnit testing
- Spring Framefork (Spring Boot, Spring MVC, Spring data JPA, Spring Security)
- Hibernate framework
- Thymeleaf (template engine)
- SuperCSV library
- Frontend tools and languages (HTML, CSS, JavaScript, Bootstrap)
- Backend tools (MySQL database, Tomcat)

The Java programming techniques that we used are:
- Interfaces
- Abstract classes
- Collections
- Custom exceptions
- Exception handling
- Method overriding
- Lambdas
- Streams
- Optionals
- File I/O
- Serialization (CSV)
- Deserializarion (CSV)
- HTTP
- Test Hooks

## Our experience developing WePlan
The development of this project was one of the hardest challenges we've had so far. Mainly because neither of us had an advanced level in Java or previous experience in web development. 

Since Spring is a Framework which is not taught in the Programming Project course, we had to learn, by ourselves, how it works and how to apply what we've learned in the course to create an interesting and useful web application.

Even if the development process was slow, difficult and in some moments frustrating, we are pretty satisfied with what we've learnt, and we are confident that this new knowledge will be useful in the future.


## Thanks for using WePlan
