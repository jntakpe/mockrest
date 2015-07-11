package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.Authority;
import com.github.jntakpe.mockrest.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mockrest.domain.Authority}
 *
 * @author jntakpe
 */
@Service
public class AuthorityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityService.class);

    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    /**
     * Recherche d'un rôle en fonction de son nom
     *
     * @param name nom du rôle
     * @return le rôle correspondant au nom
     */
    public Optional<Authority> findByName(String name) {
        LOGGER.debug("Finding authority with name {}");
        return authorityRepository.findByName(name);
    }
}
