package ZadanieRekrutacyjne.ZadanieRekrutacyjne;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Security.Security;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity")
@EnableJpaRepositories("ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories")
@Import(Security.class)
public class ZadanieRekrutacyjneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZadanieRekrutacyjneApplication.class, args);
    }
}