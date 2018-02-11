# Spring Boot REST API Sample

[![Build Status](https://travis-ci.org/tux2323/springboot-swagger-sample.svg?branch=master)](https://travis-ci.org/tux2323/springboot-swagger-sample)

The project contains a simple Java REST API server and client. The API is defined with a swagger definition see [sample-api.yaml](https://github.com/tux2323/springboot-swagger-sample/blob/master/sample-api.yaml). The server and client is generate by using the [swagger code generator](https://github.com/swagger-api/swagger-codegen). The server is based on `Spring web MVC` and the client is based on `Netflix feign`.

## Build and Run

To generate the REST server and client and to run the demo (test case) invoke: `mvn clean install`.