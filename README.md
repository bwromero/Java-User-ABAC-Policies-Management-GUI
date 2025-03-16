# **GUI for Managing Users and Clients:**

This project provides a graphical user interface (GUI) for managing users and clients within the system. It is designed primarily for administrators but also includes functionalities for non-administrative users.

## **User and Client Management:**

•	Administrators can view, create, modify, and delete users and clients.

•	Administrators can manage additional attributes for users, clients, objects, and environments.

•	Non-administrators can change their passwords after authentication.

•	Users and clients can be searched based on their attributes.

**Design Patterns:**

1. MVC (Model-View-Controller)
2. DAO (Data Access Object)
3. EAV (Entity-Attribute-Value) for the database tables

**Technologies Used:**

1.	Eclipse
2.	PostgreSQL
3.	JavaScript
4.	Redis

**External Libraries:**

1.	Swing
2.	MiGLayout
3.	RSyntaxTextArea
4.	RSTALanguageSupport
5.	Autocomplete
6.	Rhino
7.	Javassist
8.	Rs2xml
9.	Postgresql JDBC Driver
10.	Jedis


## **User Manual**

**Login**

When opening either of the two applications, the login window appears, requiring valid credentials. If the credentials are correct, the user gains access to the corresponding application.

![In a single picture](https://i.imgur.com/1jKMLYI.png)



**Managing Users and Clients**

Administrators can manage users and clients through dedicated management windows accessible via the "Users" and "Clients" tabs.

![In a single picture](https://i.imgur.com/XMIjQUw.png)

• The bottom of these windows contains "Delete" and "Add..." buttons for removing or creating users/clients.

•	Navigation buttons (First, Previous, Next, Last) allow browsing through records.

•	The top right section contains "Search..." and "Edit" buttons for searching and modifying user data.

**Viewing Users and Clients**

Navigation buttons facilitate movement between records:

![Nella Figura possiamo visualizzare i bottoni di navigazione:]( https://i.imgur.com/9YZp2TC.png)

• First - Go to the first record.
• Last - Go to the last record.
• Next - Move forward.
• Previous - Move backward.

**Creating and Deleting Users/Clients** 

**Creating a User/Client**
1.	Open the creation window using the "Add..." button.
2.	Enter the required details and confirm by clicking "OK".

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/0CqQYyA.png) 


**Deleting a User/Client**
1.	Select the user/client to delete.
2. Click "Delete" and confirm in the dialog that appears. 

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/tW4xWg3.png) 


**Editing Users and Clients**

1. Select a user/client and click "Edit".
2. Modify the necessary attributes.
3. Confirm changes by clicking "OK".

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/BZrPGVl.png) 


**Searching for Users and Clients**

1. Click "Search" in the management window.
2. Enter search criteria.
3. Select a logical operator and start the search.
4. Results will be displayed in a table, allowing further modifications or deletions.

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/JVfjW0f.png) 


**Assigning Clients to Users

1. The left table shows assigned clients.
2. The right table lists available clients.
3. Use "<<" to assign a client and ">>" to remove an assignment.
   
![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/HkMrUxJ.png) 



6.2.6	Managing Attributes

1. "Users and Clients Attributes" tab
2. "Object and Environment Attributes" tab
 
![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/FCvdnvH.png) 

**Creating an Attribute**

1. Click "Add...".
2. Enter the attribute name and type.
3. Confirm with "OK".

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/FUKoFtO.png)

**Editing an Attribute**
1. Select an attribute and click "Edit".
2. Modify the details and confirm with "OK".

**Deleting an Attribute**
1. Select the attribute.
2. Click "Delete" and confirm in the dialog.

**Managing Access Control Policies and User Preferences**

Policies and preferences are managed via dedicated windows:

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/ZKJ975e.png)

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/xQ4mAwp.png)
            
1. "Access Control Policies" (Admin application)
2. "User Preferences" (Non-admin application)

** Exporting to Redis**

1. In the Policies/Preferences management windows, click "Export...".
2. Confirm the operation.


**Managing JavaScript Functions**

JavaScript functions are managed via the "JavaScript Functions" tab.

1. The left panel lists available functions.
2. The right side contains a JavaScript editor displaying the function code.

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/RJsIfti.png)

**Creating a Function**
1. Click "Add...".
2. Write the function in the editor.
3. Validate the function.
4. Confirm creation if no syntax errors are found.

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/QrMkZKi.png)


**Editing a Function**
1. Select a function and click "Edit".
2. Modify the function, validate, and confirm.

**Deleting a Function**
1. Select a function and click "Delete".
2. Confirm the deletion.

**Creation and Deletion**

Creation
1. To create a new Policy/Preference, click the "Add ..." button located in the Policies/Preferences Management window. This will open the creation window, as shown in Figure 18.

2. In this window, enter the necessary details for creating the Policy/Preference. For Policies, it is mandatory to specify the User or Client ID.
   
3. To create a Parametric Predicate, use the attributes of User/Client, Object, and Environment, combined with JavaScript functions. To view these attributes, type the initial letter of the desired entity (s for Subject, e for Environment, o for Object, and f for functions), followed by a dot (.).

4. For example, to see all Subject attributes, type "s." in the Parametric Predicate editor, as shown in Figure 19.

5. The available attributes will appear as suggestions, and you can select them using the buttons or pointer. The auto-complete mechanism is similar to Eclipse's.
Once the function is validated (similar to the function creation window shown in Figure 16), confirm the creation by clicking "OK".

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/vIDK6OO.png)

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/UoMaKGT.png)

**Deletion**

1. Deleting a Policy/Preference follows a process similar to deleting Users/Clients.
2. Navigate to the Policy/Preference you want to delete using the navigation buttons or select the Preference ID from the management windows and click "Delete".

**Search**

1. The search function for Access Control Policies and User Preferences works differently from searching for Users/Clients.
2. To search for a Policy/Preference, open the search window by clicking the "Search ..." button in the Policies/Preferences Management windows (Figures 13 and 14).
3. For Policies, you must specify the User/Client ID.
4. Click "Search" to populate the results table with all Policies associated with the selected ID.
5. To refine the results, enter a "Topic Filter Expression" in the field at the top of the results table.

![Nella Figura possiamo visualizzare i bottoni di navigazione:](https://i.imgur.com/V5d2KD4.png)



**Modification**

**Modifying an Access Control Policy:**

1. Editing a Policy follows a similar process to modifying Users/Clients.
2. Clicking "Edit..." opens a window similar to the policy creation window shown in Figure 18, and the modification process follows the same steps as creation.
