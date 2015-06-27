package com.github.jntakpe.mockrest.repository;

import com.github.jntakpe.mockrest.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes de gestion d'entité génériques descendantes de la classe {@link AbstractEntity}
 *
 * @author jntakpe
 */
public interface GenericRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {

}
