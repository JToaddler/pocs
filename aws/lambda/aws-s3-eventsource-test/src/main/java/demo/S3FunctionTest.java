package demo;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3FunctionTest implements RequestHandler<S3Event, Map<String, String>> {

	private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

	public S3FunctionTest() {
	}

	// Test purpose only.
	S3FunctionTest(AmazonS3 s3) {
		this.s3 = s3;
	}

	@Override
	public Map<String, String> handleRequest(S3Event event, Context context) {
		context.getLogger().log("Received event: " + event);

		context.getLogger().log("Event Name :" + event.getRecords().get(0).getEventName());

		// Get the object from the event and show its content type
		String bucket = event.getRecords().get(0).getS3().getBucket().getName();
		String key = event.getRecords().get(0).getS3().getObject().getKey();
		try {
			S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
			String contentType = response.getKey();
			context.getLogger().log("File Name : " + contentType);
			if (contentType.endsWith("xml")) {
				throw new AmazonS3Exception("Invalid File uploaded into S3. Unable to process the file " + contentType);
			}
			Map<String, String> result = new HashMap<>();
			result.put("key", contentType);
			result.put("extension", response.getObjectMetadata().toString());
			context.getLogger().log("Result " + result.toString());
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			context.getLogger().log(String.format("Error getting object %s from bucket %s. Make sure they exist and"
					+ " your bucket is in the same region as this function.", key, bucket));
			throw e;
		}
	}
}