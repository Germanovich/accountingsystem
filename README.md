# Accounting system

## Techologies
Project is created with:

Java SDK version: 11.0.4 (Project language level - 8)
## Configuration table
  
   | Components  | Technology  | 
   | :---        |    :----:   |   
   | Backend   | Spring Boot 2.3.5, Java 11.0.4 |
   | Security |  Spring Security, JWT |
   | Database | MySQL |
   | Server | Tomcat 9.0.41 |
   | API testing| POSTMAN |
   | Tool | Intellj Idea |
   
   An example of a Database Dump is located in the _FH_data_filling.sql_ file. Sample Database have 5 test users:
   
   |Id|Login|Password|Role|
   |:----:|:----:|:----:|:----:|
   |1|Jack123|test|ADMIN|
   |2|SophiaDonaldson|test|USER|
   |3|Mike1999|test|USER|
   |4|Evie79|test|USER|
   |5|JacobCarey92|test|USER|
   
## Project installation
1. Navigate into the repository directory

2. Initialize the database: database/db.bat

3. Build the project using [maven](http://maven.apache.org/):

		mvn clean install
		
4. Copy war file to tomcat

## Author
Dmitriy Hermanovich - dimagermanovich@gmail.com
