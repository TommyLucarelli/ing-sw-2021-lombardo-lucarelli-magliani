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

After cloning the repository, run `mvn package` to build the project and run tests.

### Starting the project

**Requirements**: Java 15, Maven.

Move to the `target` directory, then:

- **Server**: run `java -jar PSP02 -s <port>`
- **GUI**: run `java -jar PSP02 -c <server_ip>:<server_port>`
- **CLI**: run `java -jar PSP02 -c -cli <server_ip>:<server_port>`



