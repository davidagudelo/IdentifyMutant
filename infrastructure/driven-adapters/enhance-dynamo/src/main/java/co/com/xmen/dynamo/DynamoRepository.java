package co.com.xmen.dynamo;

import co.com.xmen.dynamo.model.Human;
import co.com.xmen.model.dynamo.HumanGateway;
import co.com.xmen.model.response.HumantResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.List;
import java.util.stream.Collectors;

//Adaptador para realizar las operaciones contra la base de datos
@Component
public class DynamoRepository implements HumanGateway {

    private final DynamoDbAsyncTable<Human> humanDynamoDbAsyncTable;
    private final DynamoDbAsyncClient Client;
    private final String dynamoDbTable;

    public DynamoRepository(DynamoDbAsyncClient Client, DynamoDbEnhancedAsyncClient enhancedAsyncClient,
                            @Value("${dynamodb.tbl-name.human}") String dynamoDbTable) {
        this.Client = Client;
        this.dynamoDbTable = dynamoDbTable;
        this.humanDynamoDbAsyncTable = enhancedAsyncClient.table(dynamoDbTable, TableSchema.fromBean(Human.class));
    }

    //Metodo para guardar las cadenas de ADN en la base de datos
    @Override
    public Mono<Boolean> saveRecord(List<String> dna, boolean state) {
        //Se crea el objeto Human y se le agregan las propiedades adn y state para diferenciar si es un mutante o no
        Human human = new Human();
        String cadena = dna.stream().map(Object::toString).collect(Collectors.joining(" "));
        human.setAdn(cadena);
        human.setStatus(state);
            return Mono.fromFuture(humanDynamoDbAsyncTable.putItem( human))
                    .subscribeOn(Schedulers.elastic())
                    .thenReturn(Boolean.TRUE);
    }


    //Metodo para objener el estado de los registros
    @Override
    public Mono<HumantResult> getlist() {
        ScanRequest scanRequest = ScanRequest.builder().tableName(dynamoDbTable).build();


       return Mono.fromFuture(Client.scan(scanRequest)).map(scanResponse -> {
            return HumantResult.builder().count_human_dna(scanResponse.scannedCount())
                    .build();
       });
     }
    }


