package co.com.xmen.config;

import co.com.xmen.model.dynamo.HumanGateway;
import co.com.xmen.usecase.verify.UseCasesVerify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//bean para la configuración de los casos de USO
@Configuration
public class UseCasesConfig {

        @Bean
        public UseCasesVerify useCasesVerify(HumanGateway humanGateway) {
                return new UseCasesVerify(humanGateway);
        }

}
