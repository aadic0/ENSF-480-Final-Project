These are the **BOUNDARY** classes for the design project.

These are the classes that are the link between the **USER** and the **CONTROL** classes

How to run:

**note: execute these commands from the boundary directory

Compile: javac -cp "../../;../lib/mysql-connector-j-9.1.0.jar" login.java ../control/*.java ../entity/*.java

Compile all files in Boundary:

javac -cp "../../;../lib/mysql-connector-j-9.1.0.jar" *.java ../control/*.java ../entity/*.java



run program (for now): 
java -cp "../../;../../../lib/mysql-connector-j-9.1.0.jar" objects.boundary.Login       