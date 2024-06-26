# This is a simple Address Book API Application built using Java and Spring Boot

## Introduction

This is a simple Java Spring Boot application which have built to maintain an Address Book API .The API allows users to perform
CRUD (Create, Read, Update, Delete) operations on address book records, with all data stored in a JSON flat file.

## Features

List Records: Retrieve a list of all address book entries.
View Record: Retrieve details of a specific address book entry by email.
Add Record: Add a new entry to the address book.
Edit Record: Update details of an existing address book entry.
Delete Record: Remove an entry from the address book.

## Data Storage
All data is stored in a single JSON flat file (dataOfRecords.json), making it easy to manage and move without requiring a
database.

##  Technologies Used
*  Java
*  Spring Boot

##  IDE used
* IntelliJ IDEA

## Implementation

Since I am following a TDD development. I wrote the test case first then implement the code to make it pass, and then move
on to the next test case. This ensures that working with code that is tested and verified.

## Testing

Testing was done by using unit test cases as well as using Postman by sending HTTP requests -> GET, POST, PUT, DELETE

List Records: GET
http://localhost:8080/api/records/getall

View Record by Email : GET
http://localhost:8080/api/records/{email}

Add a Record: POST
http://localhost:8080/api/records/save

Edit a Record: PUT
http://localhost:8080/api/records/update

Delete a Record: DELETE
http://localhost:8080/api/records/delete/{email}
