package co.com.xmen.usecase;

import co.com.xmen.model.dynamo.HumanGateway;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class UseCasesVerifyTest {

    @Mock
    HumanGateway humanGateway;

    @Spy
    UseCasesVerify   useCasesVerify;

    @Test
    public void routerTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //setUp



    }
}
