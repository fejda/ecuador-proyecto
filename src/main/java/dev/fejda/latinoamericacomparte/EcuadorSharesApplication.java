package dev.fejda.latinoamericacomparte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.fejda.latinoamericacomparte")  // ← AGREGA ESTA LÍNEA
public class EcuadorSharesApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcuadorSharesApplication.class, args);
    }
}