# Progetto di Ingegneria del Software - Group PSP02 
### Politecnico di Milano - Academic Year 2020/21

## Participants:
- Martina Magliani - CP: 10682333
- Giacomo Lombardo - CP: 10674987
- Tommaso Lucarelli - CP: 10620739

Grade: 29

## Description

The aim of the project is to develop a Java software to simulate a real-world table game: Masters of Renaissance. The proposed solution consists of a client-server application, communicating through a communication protocol built on top of the TCP protocol and using JSON and Google's Gson to send representations of game's objects. The client application consists of both a CLI tool and a GUI realized with JavaFX and FXML.

### Implementation

The project includes:

- High-level UML Application Diagram
- Final UML Application Diagrams representing the real implementation
- An executable JAR file 
- Source code
- Test units

## Features

- Complete rules
- CLI
- GUI
- Socket
- Multiple games (*Advanced feature*)
- Disconnection resilience (*Advanced feature*)


## Getting started

In order to build and execute this project Java 15 is required.

After cloning the repository, run `mvn install` to build the project and run tests.

### Starting the project

**Requirements**: Java 15, Maven.

After building the project, run `cd target` and then: 

- **Server**: run `java -jar PSP02.jar -s <port>`
- **GUI**: run `java -jar PSP02.jar -c <server_ip>:<server_port>`
- **CLI**: run `java -jar PSP02.jar -c -cli <server_ip>:<server_port>`

Otherwise, if you don't have Maven installed on your computer, a pre-built jar is included in the project: run `cd deliverables/final/jar` and then run the commands above to start the project.

## Copyrights

*Image rights reserved and property of Cranio Creations.*
