package co.com.xmen.api;

import co.com.xmen.model.request.RequestMutant;
import co.com.xmen.usecase.verify.UseCasesVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

   private final UseCasesVerify usecaseVerify;

    public Mono<ServerResponse> analyzeHuman(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestMutant.class).map(req -> req.getDna())
                .flatMap(dna -> usecaseVerify.verifyHuman(dna)).filter(b -> b)
                .flatMap(b -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.status(HttpStatus.FORBIDDEN).build());
    }
}
