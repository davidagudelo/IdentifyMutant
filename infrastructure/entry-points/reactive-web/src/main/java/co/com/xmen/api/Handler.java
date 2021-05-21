package co.com.xmen.api;

import co.com.xmen.model.request.RequestMutant;
import co.com.xmen.model.response.HumantResult;
import co.com.xmen.usecase.verify.UseCasesVerify;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class Handler {

    //Caso de uso con la logica  para verificar el adn
    private UseCasesVerify usecaseVerify;

    //Se recibe la transacción y se mapea a la  RequestMutant esta lista
    //se envia al metodo verifyHuman
    public Mono<ServerResponse> analyzeHuman(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestMutant.class).map(req -> req.getDna())
                .flatMap(dna -> usecaseVerify.verifyHuman(dna)).filter(b -> b)
                .flatMap(b -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.status(HttpStatus.FORBIDDEN).build());
    }

    //Se recibe  solicita la lista a la clase de negocio
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        Mono<HumantResult> result = usecaseVerify.list();
        return ServerResponse.ok().body(result, HumantResult.class);
    }
}
