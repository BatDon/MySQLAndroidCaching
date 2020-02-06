var express = require('express');
var app = express();
var mysql = require('mysql');


var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "MySQLAndroidCaching"
});


con.connect(function(err) {
  if (err) {
    console.log("Error connecting to database")
    throw err;
  }
  console.log("Connected!");
});


var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));

app.get('/', function (req, res) {
    res.sendFile(__dirname+'/Index2.html');
});

app.post('/androidInputClient',function(req,res){
  console.log("posting in /androidInputClient");
  console.log(req.body);
});


app.post('/clientData', function (req, res) {
  console.log("posting in /clientData");
  var createClient={
  'firstName':req.body.firstName,
  'lastName':req.body.lastName,
  'address':req.body.address,
  'phone':req.body.phone
}


console.log(" firstName==== "+createClient.firstName);
let stmt = `INSERT INTO clientsInfo (firstName,lastName,address,phone)
            VALUES(?,?,?,?)`;
let todo = [createClient.firstName,createClient.lastName,createClient.address,createClient.phone];

con.query(stmt, todo, (err, results, fields) => {
  if (err) {
    response.end("Contact Admin - Not Working");
    return console.error(err.message+"ERROR IN CREATION");
  }
});

res.end("Finished Script");
});

var server = app.listen(5000, function () {
    console.log('Node server is running..');
});
