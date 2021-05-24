package co.com.xmen.usecase;

import co.com.xmen.model.dynamo.HumanGateway;
import co.com.xmen.model.response.HumantResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UseCasesVerifyTest {

    @Mock
    HumanGateway humanGateway;

    UseCasesVerify   useCasesVerify;

    char[][] matriz;
    List<String> dna;

    Mono<HumantResult> humantResultMono;

    @Before
    public void init() {

        useCasesVerify = new UseCasesVerify (humanGateway);
        dna = new ArrayList();
        dna.add("CCCCCA");
        dna.add("CCGCGA");
        dna.add("CTCCGA");
        dna.add("CTGCGA");
        dna.add("CTGCGA");
        dna.add("CTGCGA");

        matriz = useCasesVerify.listToMatriz(dna);
        humantResultMono = Mono.just(HumantResult.builder()
                .count_mutant_dna(1)
                .count_human_dna(1)
                .ratio(1.0)
                .build());

    }

    @Test
    public void listToMatrizTest() {
       assertNotNull(matriz);
    }

    @Test
    public void validarHorizontalTest() {
        int result = useCasesVerify.validarHorizontal(0,0,matriz,'C');
        assertEquals(result,1);
    }

    @Test
    public void validarVerticalTest() {
        int result = useCasesVerify.validarVertical(0,5,matriz,'A');
        assertEquals(result,1);
    }

    @Test
    public void validarOblicuaTest() {
        int result = useCasesVerify.validarOblicua(0,0,matriz,'C');
        assertEquals(result,1);
    }

    @Test
    public void verifyMutantTest() {
         Mono<Boolean> result =  useCasesVerify.verifyMutant(matriz);
         result.flatMap(s -> {
            assertTrue(s);
             return null;
         });
    }

    @Test
    public void verifyHumanTest() {
        Mono<Boolean> result =  useCasesVerify.verifyHuman(dna);
        result.flatMap(s -> {
            assertTrue(s);
            return null;
        });
    }
}
