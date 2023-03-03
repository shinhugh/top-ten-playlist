package org.dev.toptenplaylist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TopTenPlaylistApplication {
    public static void main(String[] args) {
        SpringApplication.run(TopTenPlaylistApplication.class, args);
    }
}
