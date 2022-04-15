package com.example.demo;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.util.EC2MetadataUtils;

@RestController
public class DemoWorldAPI {

	String ec2MetaDate = null;

	@PostConstruct
	public void loadMetaData() {
		ec2MetaDate = DemoWorldAPI.getEC2MetaData();
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	private String home() {
		return "Hello " + ec2MetaDate;
	}

	@RequestMapping(path = "/getAllCountry", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	private Response getAllCountry(@RequestBody(required = false) Request request) {

		Response resp = new Response();
		resp.setMessage("Hello " + ec2MetaDate);
		List<Country> countries = null;

		try {

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("world.ser.data");
			ObjectInputStream in = new ObjectInputStream(inputStream);
			final List<Country> countryList = (List<Country>) in.readObject();
			in.close();

			if (request == null) {
				resp.setCountries(countryList);
				return resp;
			}

			countries = countryList.stream().filter((country) -> {

				if (StringUtils.isNotBlank(request.getContinentName())
						&& StringUtils.isBlank(request.getCountryCode())) {
					return country.getContinent().equalsIgnoreCase(request.getContinentName());
				} else if (StringUtils.isBlank(request.getContinentName())
						&& StringUtils.isNotBlank(request.getCountryCode())) {
					return country.getCode().equalsIgnoreCase(request.getCountryCode());
				} else if (StringUtils.isNotBlank(request.getContinentName())
						&& StringUtils.isNotBlank(request.getCountryCode())) {
					return country.getCode().equalsIgnoreCase(request.getCountryCode())
							&& country.getContinent().equalsIgnoreCase(request.getContinentName());
				} else if (StringUtils.isBlank(request.getContinentName())
						&& StringUtils.isBlank(request.getCountryCode())) {
					return true;
				} else
					return false;

			}).collect(Collectors.toList());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setCountries(countries);
		return resp;
	}

	private static String getEC2MetaData() {

		try {
			// Getting instance Id
			String instanceId = EC2MetadataUtils.getInstanceId();

			// Getting EC2 private IP
			String privateIP = EC2MetadataUtils.getInstanceInfo().getPrivateIp();

			// Getting EC2 public IP
			AmazonEC2 awsEC2client = AmazonEC2ClientBuilder.defaultClient();
			String publicIP = awsEC2client.describeInstances(new DescribeInstancesRequest().withInstanceIds(instanceId))
					.getReservations().stream().map(Reservation::getInstances).flatMap(List::stream).findFirst()
					.map(Instance::getPublicIpAddress).orElse(null);

			return " from " + instanceId + " - " + privateIP + " - " + publicIP;
		} catch (Exception e) {
			return "";
		}

	}

}