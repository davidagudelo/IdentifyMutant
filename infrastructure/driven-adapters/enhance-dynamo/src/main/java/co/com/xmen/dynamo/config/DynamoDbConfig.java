package co.com.xmen.dynamo.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClientBuilder;
//Configuración para establecer los bean de conexión contra dynamo
@Configuration
public class DynamoDbConfig {

	@Value("${aws.dynamodb.endpoint:}")
	private String dynamoDbEndPointUrl;

	//Se crea la conexión a dynamo
	@Bean
	public DynamoDbAsyncClient getDynamoDbAsyncClient() {
		DynamoDbAsyncClientBuilder builder = DynamoDbAsyncClient.builder();
		if (dynamoDbEndPointUrl != null && !dynamoDbEndPointUrl.isEmpty()) {
			builder.endpointOverride(URI.create(dynamoDbEndPointUrl));
		}
		return builder.build();
	}

	@Bean
	public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient() {
		return DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(getDynamoDbAsyncClient()).build();
	}


}
