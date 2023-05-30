package com.carloser7.er7money.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;
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
		
		String bucketName = this.apiProperty.getS3().getBucketPrefix();
		if (!amazonS3.doesBucketExistV2(bucketName)) {
			amazonS3.createBucket(new CreateBucketRequest(bucketName));
		}
		
		BucketLifecycleConfiguration.Rule regraExpiracao = new BucketLifecycleConfiguration.Rule()
			.withId("Regra de expiração de arquivos temporários")
			.withFilter(
				new LifecycleFilter(
				new LifecycleTagPredicate(
					new Tag("expirar", "true"))))
			.withExpirationInDays(1)
			.withStatus(BucketLifecycleConfiguration.ENABLED);
		
		BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration().withRules(regraExpiracao);
		
		amazonS3.setBucketLifecycleConfiguration(bucketName, configuration);
		return amazonS3;
	}
}
