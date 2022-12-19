package br.com.newgo.mercado;

//import br.com.newgo.mercado.config.RsaKeyProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableConfigurationProperties(RsaKeyProperties.class)

@SpringBootApplication
public class MercadoApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MercadoApiApplication.class, args);
    }
}