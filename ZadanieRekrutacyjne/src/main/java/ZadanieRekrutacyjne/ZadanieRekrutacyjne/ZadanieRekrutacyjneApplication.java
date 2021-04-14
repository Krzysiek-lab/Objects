package ZadanieRekrutacyjne.ZadanieRekrutacyjne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities")
@EnableJpaRepositories("ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories")
public class ZadanieRekrutacyjneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZadanieRekrutacyjneApplication.class, args);
	}
}
