package co.com.xmen.dynamo;

import static org.mockito.ArgumentMatchers.any;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import co.com.xmen.dynamo.model.Human;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.Condition;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

@RunWith(SpringRunner.class)
public class DynamoRespositoryTest {

    @Mock
    DynamoDbAsyncClient asyncClient;

    @Mock
    DynamoDbEnhancedAsyncClient enhancedAsyncClient;

    @Mock
    DynamoDbAsyncTable<Human> mutantTable;

    private DynamoRepository repository;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.when(enhancedAsyncClient.table(any(), any(BeanTableSchema.class))).thenReturn(mutantTable);
        repository = new DynamoRepository(asyncClient, enhancedAsyncClient, Human.class.getSimpleName());
    }




}
