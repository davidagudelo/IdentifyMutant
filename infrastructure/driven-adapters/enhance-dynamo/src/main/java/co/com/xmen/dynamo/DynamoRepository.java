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
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                .thenReturn(state);
    }



    @Override
    public Mono<HumantResult> getlist() {
        //Se crea un scan sobre la tabla human
        ScanRequest scanRequest = ScanRequest.builder().tableName(dynamoDbTable).scanFilter(Filter())
                .build();

        return Mono.fromFuture(Client.scan(scanRequest)).map(scanResponse -> {
            return averageHuman(scanResponse);
        });

    }

    //Se construye el objeto Humant Resullt
    private HumantResult averageHuman(ScanResponse scanResponse) {
        //Se obtiene el scaner del filtro y de toda la tabla para saber
        // cantida de registros y cantidad de muntantes
        return  HumantResult.builder()
                .count_mutant_dna(scanResponse.count())
                .count_human_dna(scanResponse.scannedCount())
                .ratio(scanResponse.count()*100/scanResponse.scannedCount())
                .build();
    }


    public Map<String, Condition> Filter() {
        //Se  crea una lista de los valores de los atributos y se le agrega el valor
        //True para identificar a los mutantes
        List<AttributeValue> attributeValuesList = new ArrayList<>();
        attributeValuesList.add(AttributeValue.builder().bool(Boolean.TRUE).build());

        //Se crea una condicion para filtar todos los valores en true del campo status
        Map<String, Condition> filterValue = new HashMap<>();
        filterValue.put("status", Condition.builder().comparisonOperator(ComparisonOperator.EQ.toString())
                .attributeValueList(attributeValuesList).build());
        //Se retorna el fitlro
        return filterValue;
    }

}





