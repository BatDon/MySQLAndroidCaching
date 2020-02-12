# MySQL-Android-Caching
This app uses a Node.js script that connects to the server. The script allows the user to input their first name, last name address and phone number. Once the user submits the data the data is sent to a MySQL Database, which is hosted on a server. A PHP script then creates a connection to the database and the data is hosted on a server in JSON format. The android app then uses a get request with Retrofit to deserialize the data using GSON. The information is show in a RecyclerView. This data is then cached. When the app doesn't have an internet connection it uses the stored cached data for the RecyclerView. 

![](/RecyclerView.jpg)
![](/gifs/androidScreen.gif)
