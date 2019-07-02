# Beverage Factory

## Overview:
	This application is a web service.
	It will return total price for the given order

## Software Installation:
	java 1.8
	git
	maven

 Git repository needs to be cloned on the local machine and run following command to start an application

  

##### Build the application without test-cases
``` mvn clean install -DskipTests```
##### Run the application
``` mvn spring-boot:run```
##### Run tests
``` mvn test```

## API endpoints

#### Compute Price
Returns total cost for the given input.
> POST: http://localhost:880/api/computePrice

**Parameters:**
- query: (Required) order as string

Sample Request : 
{
	"query": ["Chai, -sugar", "Chai", "Coffee, -water,-sugar"]
}

Sample Response:

 {
     "message": "Order processed Successfully.",
     "httpStatus": "OK",
     "totalCost": "11.5",
     "individualPrices": {
         "Chai, -sugar": 3.5,
         "Coffee, -water,-sugar": 4,
         "Chai": 4
     }
 }