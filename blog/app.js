const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
const path = require('path');
const mime = require('mime');
const mysql = require('mysql');

const app = express();
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname));


const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '123',
    database: 'demo',
    multipleStatements: true,
});
connection.connect();

function getFile(res, filename) {
    fs.readFile(filename, 'utf-8', (err, data) => {
        if (err) throw err;
        res.setHeader('Content-Type', mime.getType(filename));
        res.send(data);
    })
}
app.get("/user", (req, res) => {
    let sql = `select * from user`;
    connection.query(sql, (err, data) => {
        if (err) throw err;
        res.send(data);
    })
})
app.get('/index', (req, res) => {
    getFile(res, path.join(__dirname, "pages", 'index.html'));
});
app.get('/banner', (req, res) => {
    getFile(res, path.join(__dirname, "pages", 'banner.html'));
});
app.get('/page', (req, res) => {
    getFile(res, path.join(__dirname, "pages", 'page.html'));
});
const port = process.env.PORT || 8080;

app.listen(port, () => {
    console.log('server running');
});