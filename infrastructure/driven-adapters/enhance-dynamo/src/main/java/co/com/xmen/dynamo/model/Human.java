package co.com.xmen.dynamo.model;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class Human {

	private String adn;
	private boolean status;

	@DynamoDbPartitionKey
	public String getAdn() {
		return adn;
	}

	public boolean getStatus() {
		return status;
	}

}
