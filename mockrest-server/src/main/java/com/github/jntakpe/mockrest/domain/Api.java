package com.github.jntakpe.mockrest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Entité représentant une API
 *
 * @author jntakpe
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Api extends AbstractEntity {

    @NotNull
    private String name;

    @OneToMany
    private Set<User> users;
}
