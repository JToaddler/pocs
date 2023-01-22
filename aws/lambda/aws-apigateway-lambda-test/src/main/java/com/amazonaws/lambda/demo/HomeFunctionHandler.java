package com.amazonaws.lambda.demo;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.util.Util;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class HomeFunctionHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

		context.getLogger().log("Received event " + input.getPath() + "" + input.getHttpMethod()
				+ " coming from API resoure :" + input.getResource());

		APIGatewayProxyResponseEvent response = Util.getDefaultResponse();

		Map<String, String> body = new HashMap<>();
		body.put("funcationName", "HelloFunctionHandler");
		body.put("message", "Hello");
		body.put("version", "2");
		response.setBody(body.toString());
		return response;
	}

}
