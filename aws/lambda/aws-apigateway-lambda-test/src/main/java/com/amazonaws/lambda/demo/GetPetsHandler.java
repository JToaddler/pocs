package com.amazonaws.lambda.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.util.Util;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class GetPetsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

		context.getLogger().log("Received event " + input.getPath() + "" + input.getHttpMethod()
				+ " coming from API resoure :" + input.getResource());

		APIGatewayProxyResponseEvent response = Util.getDefaultResponse();

		Map<String, Object> body = new HashMap<>();
		body.put("funcationName", "GetPetsHandler");
		body.put("pets", Arrays.asList("Dog", "Cat", "Fish", "Parrot"));
		body.put("version", "2");
		response.setBody(body.toString());

		return response;
	}

}
