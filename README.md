Title of Application: DBClient

Author: Shoshanna Thomas-McCue

Contact Information: 
(509) 560-3196 PST (GMT-8)
stho517@wgu.edu 

Application version: 1.1

Purpose of Application: This is a 
GUI-based scheduling desktop application. It
assists with customer and appointment
tracking for a global business, and allows appointments
to be seamlessly managed from anywhere in the world.

Date: 11-16-2023

IDE: IntelliJ IDEA 2023.1 (Community Edition)
Build #IC-231.8109.175, built on March 28, 2023
Runtime version: 17.0.6+10-b829.5 x86_64
macOS 12.6.6

Full JDK of version used: Java SE 17.0.6

JavaFX version compatible with JDK version: JavaFX-SDK-17.0.2-ea

Directions: Log into the program using a username and password that exist
in the mysql client_schedule.users table. Either username: "test" 
password: "test" or username: "admin" password "admin" will work.

On successful log in you will be met with the landing page. At the top left
you will be notified whether there are any appointments scheduled to start
in the next 15 minutes. Below that there is a pane that contains the
appointments table. Use the radio buttons above the appointments table on
the right to toggle between all appointments, this week's appointments, or
this month's appointments. Directly under the appointments table you can 
see three buttons, "Add", "Update", or "Delete" which will allow you to
manipulate the appointment data accordingly. 

Add pulls up a form that you must fill out to add an appointment. Please
fill out the form completely. Please choose start and end times that fall
within the business hours of 8:00am to 10:00pm (22:00) ET. The application
uses the 24 hour system to keep track of am and pm, please calibrate your
inputs accordingly. When inputting time/date information please use your
local time, no need to adjust for time zone differences. Input time/date
data in the format "mm-dd-yyyy hh:mm". Click save to save, or cancel to
discard and return to the landing page. 

All the information for the Add Appointment form also applies to the 
Update Appointment form. The only difference is that in order to open 
the Update Appointment form, you must first select an appointment from
the appointments table that you'd like to edit. 

If you select an appointment and then click the delete button, a confirmation
window will appear. You will be required to verify the appointment you would
like to delete. Once you confirm, a message will display to let you know if 
your delete was successful.

Below the appointment table is the customers table, which displays all the
customers in the system. Again on the right and directly below the customers
table you can see an "Add", "Update", and "Delete" button. These pull up
corresponding forms that work very similarly to the "Add", "Update", and 
"Delete" forms I've described for the appointments table. However, these 
allow you to manipulate the customers in the data instead of the appointments.
One key difference is that in order to delete a customer, you must first 
delete any appointments that are scheduled with that customer. 

At the very bottom of the landing page window, you will see buttons to
"View Customer Location Distribution", "View Contact Schedules",
"View Appointment Reports", and "Exit". 

When clicked, "View Contact Schedules" will open a window where any of
the contacts in the data can be selected from a dropdown. Once a contact
is selected, all of their scheduled appointments will display in the
Upcoming Appointments For Selected Contact table. Click the Exit button
to return to the landing page. 

The "View Appointment Reports" button will open a window displaying 
two tables. The one on the left shows how many appointments are scheduled
in each month. The one on the right will show how many appointments are
schedules of each type.

The additional report I ran for part A3f of this project is a report on
where the businesses customers are located. It's located under the 
"View Customer Location Distribution" button on the landing page. It
displays two tables, one showing how many customers are located in each 
First Level Division, and one showing how many customers are located in
each country.

the MySQL Connector driver version number, including the update number: mysql-connector-java-8.1.0
