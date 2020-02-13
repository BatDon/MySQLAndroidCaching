# MySQL-Android-Caching
This app allows users to add and read client data from a MySQL Database. The user can post data using a webform or post using the save client page. The user can input the first name, last name, address and phone number. The requests for client data are cached, so that an internet connection isn't necessary, provided there was an initial connection.

![](/gifs/androidScreen.gif)
<a href="https://imgflip.com/gif/3oz0t3"><img src="https://i.imgflip.com/3oz0t3.gif" title="made at imgflip.com"/></a>

## How To Setup and Use
This app uses XAMPP as its local server. It also uses MySQL as its database. XAMPP hosts the app2.js file, the MySQL Database, the index.php script. The index.php script hosts the index2.html, which allows the user to input client data on a webform. When the user clicks submit button on the webform. The database is updated via the app2.js scipt. The app2.js scipt inserts the data into the MySQL Database. The index.php script reads the MySQL data and puts it into JSON format. The android app then uses Retrofit to read this JSON format. Retrofit uses GSON to serialize and deserialize the data. The base URL is the local ip address for the computer that is using XAMPP. This data is displayed in a RecyclerView. When on the Android app, if the user clicks save on the save client page. That sends a post request via Retrofit to app2.js, which then updates the MySQL Database. 

