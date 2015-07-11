package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.domain.Authority;
import com.github.jntakpe.mockrest.domain.UserApi;
import com.github.jntakpe.mockrest.repository.UserApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Services associés à l'entité {@link UserApiService}
 *
 * @author jntakpe
 */
@Service
public class UserApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiService.class);

    private UserApiRepository userApiRepository;

    private UserService userService;

    private AuthorityService authorityService;

    @Autowired
    public UserApiService(UserApiRepository userApiRepository, UserService userService, AuthorityService authorityService) {
        this.userApiRepository = userApiRepository;
        this.userService = userService;
        this.authorityService = authorityService;
    }

    /**
     * Créée un lien entre l'utilisateur courant et une api
     *
     * @param api api avec laquelle faire le lien
     * @return lien entre l'api et l'utilisateur courant
     */
    @Transactional
    public UserApi create(Api api) {
        UserApi userApi = new UserApi();
        userApi.setApi(api);
        userApi.setUser(userService.getCurrentUserOrCreateTemp());
        userApi.setAuthority(authorityService.findByName(Authority.ROLE_ADMIN).get());
        LOGGER.info("Creating UserApi {}", userApi);
        return userApiRepository.save(userApi);
    }

}
