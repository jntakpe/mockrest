package com.github.jntakpe.mockrest.repository;


import com.github.jntakpe.mockrest.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes de gestion de l'entité {@link Authority}
 *
 * @author jntakpe
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
