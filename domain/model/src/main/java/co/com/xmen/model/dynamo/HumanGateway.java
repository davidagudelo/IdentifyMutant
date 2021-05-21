package co.com.xmen.model.dynamo;

import co.com.xmen.model.response.HumantResult;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HumanGateway {

    Mono<Boolean> saveRecord(List<String> dna, boolean state);

    Mono<HumantResult> getlist();

}

