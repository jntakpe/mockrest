package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.repository.ApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
