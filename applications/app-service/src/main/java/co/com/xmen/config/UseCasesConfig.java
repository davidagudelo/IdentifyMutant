package co.com.xmen.config;

import co.com.xmen.usecase.verify.UseCasesVerify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

        @Bean
        public UseCasesVerify getUseCasesVerify() {
                return new UseCasesVerify();
        }
}
