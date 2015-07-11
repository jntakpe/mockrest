package com.github.jntakpe.mockrest.service;

import com.github.jntakpe.mockrest.config.security.SecurityUtils;
import com.github.jntakpe.mockrest.config.security.SpringSecurityUser;
import com.github.jntakpe.mockrest.domain.User;
import com.github.jntakpe.mockrest.repository.UserRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mockrest.domain.User}
 *
 * @author jntakpe
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Récupère un utilisateur et ces rôles à partir de son login en ignorant la case
     *
     * @param login login de l'utilisateur recherché
     * @return l'utilisateur correspondant au login
     * @throws UsernameNotFoundException si ce login n'existe pas en base de données
     */
    @Transactional(readOnly = true)
    public User findByLoginWithAuthorities(String login) {
        User user = findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found in DB"));
        LOGGER.trace("Fetching authorities for user {}", login);
        Hibernate.initialize(user.getAuthorities());
        return user;
    }

    /**
     * Récupère un utilisateur à partir de son login
     *
     * @param login login de l'utilisateur recherché
     * @return l'utilisateur correspondant au login
     */
    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        LOGGER.debug("Searching user with username {} from DB", login);
        return userRepository.findByLoginIgnoreCase(login);
    }

    /**
     * Récupère un utilisateur à partir de son identifiant
     *
     * @param id identifiant de l'utilisateur
     * @return utilisateur correspondant à l'identifiant
     */
    @Transactional(readOnly = true)
    public User findById(Long id) {
        LOGGER.debug("Searching user with id {}");
        return userRepository.findOne(id);
    }

    /**
     * Créé un utilisateur temporaire
     *
     * @return utilisateur temporaire créé
     */
    @Transactional
    public User createTemporary() {
        User tmpUser = User.createTmp(findTmpUserName());
        LOGGER.info("Creating temporary user {}", tmpUser);
        return userRepository.save(tmpUser);
    }

    /**
     * Récupère l'utilisateur courant s'il existe sinon créé un utilisateur temporaire
     *
     * @return l'utilisateur courant ou celui créé temporairement
     */
    @Transactional
    public User getCurrentUserOrCreateTemp() {
        return SecurityUtils.getCurrentUser()
                .map(SpringSecurityUser::getId)
                .map(this::findById)
                .orElseGet(this::createTemporary);
    }

    private String findTmpUserName() {
        Long nbTemporary = userRepository.countByTemporary(true);
        LOGGER.trace("Counting {} temporary users", nbTemporary);
        return User.TMP_PREFIX + ++nbTemporary;
    }
}
