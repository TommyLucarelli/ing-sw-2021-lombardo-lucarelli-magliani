# Progetto di Ingegneria del Software - Group PSP02 
### Politecnico di Milano - Academic Year 2020/21

## Participants:
- Martina Magliani - CP: 10682333
- Giacomo Lombardo - CP: 10674987
- Tommaso Lucarelli - CP: 10620739
---
## Features

- Complete rules
- CLI
- GUI
- Socket
- (FA) Multiple games
- (FA) Disconnection resilience

---
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



