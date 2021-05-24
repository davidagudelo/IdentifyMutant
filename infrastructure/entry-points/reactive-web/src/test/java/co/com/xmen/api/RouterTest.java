package co.com.xmen.api;

import co.com.xmen.model.response.HumantResult;
import co.com.xmen.usecase.UseCasesVerify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    UseCasesVerify useCasesVerify;

    static final String URI_STATS = "/stats";
    static final String URI_MUTANT = "/mutant";

    @Before
    public void init(){

        Mono<HumantResult> humantResult = Mono.just(HumantResult.builder().count_human_dna(100).count_mutant_dna(40).ratio(1.4).build());
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofMillis(50000))
                .build();

        when(useCasesVerify.verifyHuman(any())).thenReturn(Mono.just(false));
        when(useCasesVerify.list()).thenReturn(humantResult);


    }


    @Test
    public void testStats(){

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(URI_STATS)
                        .build())
                .header("Content-Type","application/json")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void testMutant(){
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(URI_MUTANT)
                        .build())
                .header("Content-Type","application/json")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }


}
