package com.github.jntakpe.mockrest.config.security;

import com.github.jntakpe.mockrest.config.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

/**
 * Classe utilitaire de Spring Security
 *
 * @author jntakpe
 */
public final class SecurityUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    private SecurityUtils() {
    }

    /**
     * Récupère l'utilisateur Spring Security courant
     *
     * @return l'utilisateur courant
     */
    public static Optional<SpringSecurityUser> getCurrentUser() {
        Authentication authentication = getAuthentification();
        SpringSecurityUser springSecurityUser;
        if (authentication != null && authentication.getPrincipal() instanceof SpringSecurityUser) {
            springSecurityUser = (SpringSecurityUser) authentication.getPrincipal();
            return Optional.of(springSecurityUser);
        }
        return Optional.empty();
    }

    /**
     * Vérifie si l'utilisateur est authentifié
     *
     * @return {@code true} si l'utilisateur est authentifié sinon {@code false}
     */
    public static boolean isAuthenticated() {
        Collection<? extends GrantedAuthority> authorities = getAuthentification().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(ConfigConstants.ANONYMOUS)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Authentication getAuthentification() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
