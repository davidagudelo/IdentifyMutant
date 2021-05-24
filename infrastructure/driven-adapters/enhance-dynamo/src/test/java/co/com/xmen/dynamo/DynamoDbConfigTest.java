package co.com.xmen.dynamo;

import co.com.xmen.dynamo.config.DynamoDbConfig;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class DynamoDbConfigTest {

    @Test
    public void getDynamoDbAsyncClientNotNull() {
        DynamoDbConfig config = new DynamoDbConfig();
        config.setDynamoDbEndPointUrl("http://localhost:8000");
        assertNotNull(config.getDynamoDbEnhancedAsyncClient());
    }
}
