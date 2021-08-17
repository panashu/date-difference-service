# Calculate date difference between two dates

This service offers Rest API that takes in any date and returns the difference in number of days between the given date (the input) and the current date (Today’s date).



# Approach towards the assignment

- Technical choices : Spring Boot application with AWS Lambda and Cloud formation.

- **Springboot**: It is very easy to develop Spring Based applications with Java. It reduces lots of development time and increases productivity. It avoids writing lots of boilerplate Code, Annotations and XML Configuration to to improve Development, Unit Test and Integration Test Process.

- **AWS Lambda**: AWS Lambda provides low cost compute with zero maintenance. Lambda runs our code on demand, without provisioned and managed servers. Lambda automatically runs and scales our code. Since AWS Lambda has the ability to process concurrent requests and because they can be triggered by events on AWS API Gateway, these resources are chosen as the building blocks to the solution of a light weight application that offers REST API to calculate the date difference between two dates.

- **AWS Cloud formation**: To make this infrastructure reproducible, version controlled and available as code (IaC), Cloud formation scripts is used. 


- Key Technical Features 

	- **Handler and Configuration** - The LambdaHandler class is created in the same package as the application. This code is the same as that is detailed by the AWS Serverless Java container quick start. The only addition is to enable the lambda Spring Boot profile. handler.activateSpringProfiles("lambda");
	A standard Spring Boot configuration class Config is created. We are disabling beans used for HTTP handling and optimizing for Lambda execution. @EnableWebMvc is required for Lambda deployment. The @Profile annotation makes these beans only load when the Lambda profile is enabled. Thus, the code can still run as a Spring Boot application outside of Lambda.
	
	- **Deployment with SAM** - AWS CloudFormation provides a common language for describing and provisioning all the infrastructure resources in your cloud environment.
	AWS SAM is a model used to define serverless applications on AWS. SAM is based on Cloudformation and provides a simplified syntax. Using SAM greatly simplifies the code to create serverless applications. A sam.yaml template is defined for this project.



# Build

- Spring boot dependencies
	- **spring-boot-maven-plugin**: To provide main manifest attribute, repackage goal is added to execution goals. Otherwise, we need to call the plugin explicitly as mvn package spring-boot:repackage. With the goal added, we have to call only mvn package.
	- **spring-boot-maven-plugin**: It packages all dependencies into one uber-jar. It can also be used to build an executable jar by specifying the main class. This plugin is particularly useful as it merges content of specific files instead of overwriting them by Relocating Classes.
	
# Run

- To build and run from a packaged jar locally:

    mvn spring-boot:run

or 

    mvn clean package -Dboot
    java -jar target/date-difference-service-1.0.0-SNAPSHOT.jar


# Deployment steps

The deployment steps will require AWS CLI to be installed and configured. Following are the steps to deploy the Lambda:

-**1**- Clean and rebuild the code as a shaded jar, not as a Spring Boot jar.
	
	- mvn clean package
	
-**2**- Create a S3 bucket to hold the application code. This bucket name must be unique across S3, so adjust for your use in the next two steps.

	- aws s3 mb s3://date-difference-service-20082021

-**3**- Copy the jar file to the S3 bucket and update the information into a SAM template.

	- aws cloudformation package --template-file sam.yaml --output-template-file target/output-sam.yaml --s3-bucket date-difference-service-20082021

-**4**- Deploy a Cloudformation stack from the SAM template. We must provide the --capabilities to allow the deploy to succeed because SAM will be creating IAM roles and policies needed to allow the API Gateway to execute the Lambda function.

	- aws cloudformation deploy --template-file target/output-sam.yaml --stack-name date-difference-service --capabilities CAPABILITY_IAM

-**5**- Describe the stack, which will display the URL of the API in the outputs.

	- aws cloudformation describe-stacks --stack-name date-difference-service
    
    
# Test

The API can now be tested with cURL or a REST Client.
    curl http://localhost:8080/api/datedifference?inputDate=<yyyy-mm-dd>
    
# Update Code

We have two choices when we need to update the Lambda code. We can delete and re-create the entire cloud formation stack or simply update the code directly on the Lambda function.

	- Make any change in the API source code.
	- First, find the full name of the Lambda: aws lambda list-functions
	- Build the updated code : mvn clean package
	- Update the function. Be sure to adjust the full function-name
		
		aws lambda update-function-code --function-name date-difference-serviceDateDifferenceFunction --zip-file fileb://target/date-difference-service-1.0.0-SNAPSHOT.jar

# Monitoring

 CloudWatch stores the logs of the Lambda application and provides filtering. 
 
# Cleanup
Although, Lambdas won't cost unless we use them, it's always a good habit to clean up unused resources in AWS.

aws cloudformation delete-stack --stack-name date-difference-service

# Swagger for visualizing API locally

Relevant specifications for consuming the REST API with the expected output is documented using OpenAPI standards.
This can be accessed at http://localhost:8080/swagger-ui.html    
