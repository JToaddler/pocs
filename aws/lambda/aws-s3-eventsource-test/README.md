HelloWorldFunction


#To Generate AWS SAM local test event:

sam local generate-event apigateway aws-proxy --method GET --body '{"test":"body"}'


# To Emit an event using SAM CLI in local

sam local generate-event apigateway aws-proxy --method GET --body '{"test":"body"}' | sam local invoke

# To debug
sam local generate-event apigateway aws-proxy --method GET --body '{"test":"body"}' | sam local invoke -d 5858

