# CSCI-455-Project-1-Server

This is the server for CSCI-455: Networking and Parallel Computation project 1.

## Authors

Brandon Gasser and Carson Miller

## What does the server do?

The server manages the connections to the Firebase realtime database and handles requests from the client. The server can handle CREATE, LIST, and DONATE requests. CREATE creates a new fundraiser event, LIST lists all fundraiser events, and DONATE adds to the balance of the specified fundraiser event.

## Logging

The server writes logs in the terminal. When a new client connects, you will see `Connected`. When a client disconnects, you will see `Closing the connection`. When the server sees a request, it will print out `HEARD` followed by the message on the next line(s). When the server is sending a response, it will print out `SENDING` followed by the message on the next line(s).

## Requirements

- Java 17+
- Gradle

## Running

Clone the repo and run `gradle run` in the project's base directory.