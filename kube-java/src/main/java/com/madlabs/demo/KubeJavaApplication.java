package com.madlabs.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;

@SpringBootApplication
public class KubeJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KubeJavaApplication.class, args);
	}

	@Override

	public void run(String... args) throws Exception {
		// ApiClient client = Config.defaultClient();
		ApiClient client = Config.fromCluster();
		client.setVerifyingSsl(false);
		Configuration.setDefaultApiClient(client);
		CoreV1Api api = new CoreV1Api();
		V1PodList list = api.listNamespacedPod("test", null, null, null, null, null, null, null, null, null);
		for (V1Pod item : list.getItems()) {
			System.out.println(item.getMetadata().getName());
		}
		
		V1PodList list2 = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
		for (V1Pod item : list2.getItems()) {
			System.out.println(item.getMetadata().getName());
		}

	}

}
