package com.github.jntakpe.mockrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe mère de l'application
 *
 * @author jntakpe
 */
@SpringBootApplication
public class MockrestServerApplication {

    /**
     * Méthode appellée pour le démarrage de l'application
     *
     * @param args paramètres de l'application
     */
    public static void main(String[] args) {
        SpringApplication.run(MockrestServerApplication.class, args);
    }
}
