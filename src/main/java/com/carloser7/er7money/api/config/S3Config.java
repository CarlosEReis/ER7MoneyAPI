package com.carloser7.er7money.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.carloser7.er7money.api.property.Er7moneyApiProperty;

@Configuration
public class S3Config {

	@Autowired
	private Er7moneyApiProperty apiProperty;
	
	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(
			this.apiProperty.getS3().getAccessKeyId(),
			this.apiProperty.getS3().getSecretAccessKey());
		
		AmazonS3 amazonS3 = AmazonS3ClientBuilder
			.standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
		return amazonS3;
	}
}
