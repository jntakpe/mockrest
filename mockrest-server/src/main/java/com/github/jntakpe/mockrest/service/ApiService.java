package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.repository.ApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mockrest.domain.Api}
 *
 * @author jntakpe
 */
@Service
public class ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    private ApiRepository apiRepository;

    private UserApiService userApiService;

    @Autowired
    public ApiService(ApiRepository apiRepository, UserApiService userApiService) {
        this.apiRepository = apiRepository;
        this.userApiService = userApiService;
    }

    /**
     * Création d'une API
     *
     * @param api api à créer
     * @return l'api créée
     */
    @Transactional
    public Api create(Api api) {
        LOGGER.info("Creating api {}", api);
        Api savedApi = apiRepository.save(api);
        userApiService.create(savedApi);
        return savedApi;
    }

    /**
     * Suppression d'une API
     *
     * @param id identifiant de l'api à supprimer
     */
    @Transactional
    public void delete(Long id) {
        Api toDelete = apiRepository.findOne(id);
        LOGGER.info("Removing API {}", toDelete);
        userApiService.remove(toDelete.getUserApis());
        apiRepository.delete(toDelete);
    }

    /**
     * Recherche une api en fonction de son nom
     *
     * @param name nom de l'API recherché
     * @return l'API correspondant au nom
     */
    @Transactional(readOnly = true)
    public Optional<Api> findByName(String name) {
        LOGGER.debug("Searching Api by name {}", name);
        return apiRepository.findByName(name);
    }
}
