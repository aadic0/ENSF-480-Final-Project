Step 1: In order to get database working, download MySQL Community Server 9.1.0 

Step 2. Open the MySQL Command Line Client

Step 3. Create the database
CREATE DATABASE THEATRE_DB;

Step 4. Create user to use to connect to database:
CREATE USER 'theatre_connect'@'localhost' IDENTIFIED WITH 'caching_sha2_password' BY 'theatre';
GRANT ALL ON THEATRE_DB.* TO 'theatre_connect'@'localhost';

Step 5: make sure user is created using this command:
SELECT User, Host FROM mysql.user WHERE User = 'theatre_connect';

Step 6. To create the database tables and run queries to fill them, run the following commands in MySQL CLI 
source Path/To/Project/database/TheatreDB.sql;
source Path/To/Project/database/queries.sql;

Step 7. with Path/To/Project/ as the current working directory, run this command to create the .class files (the jar file does not work, so please compile class files)
        Make sure that the mysql-connector-j-9.1.0.jar file is in the /lib folder
javac -cp "lib/mysql-connector-j-9.1.0.jar" src/objects/boundary/*.java src/objects/control/*.java src/objects/entity/*.java

Step 8. With the .class files created, run this command in the same current working directory
java -cp "lib/mysql-connector-j-9.1.0.jar;src" objects.boundary.appGUI

Step 9. Note that when filling out any section where you enter payment information, the CC number must be 16 digits, the CVV must be 3 digits, 
        and the expiration date should be a point in the future and be in the format of yyyy-mm-dd with dd being the last day of the month (ex: 2025-01-31)