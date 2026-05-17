package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "app",
    "application",
    "infrastructure"
})
@EntityScan("infrastructure.persistence.entities")
@EnableJpaRepositories("infrastructure.persistence.repositories")
public class SantiagotabaresApplication {

    public static void main(String[] args) {
        SpringApplication.run(SantiagotabaresApplication.class, args);
    }
}
