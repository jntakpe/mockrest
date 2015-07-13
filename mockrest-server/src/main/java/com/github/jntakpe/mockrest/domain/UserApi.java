package com.github.jntakpe.mockrest.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entité représentant la relation entre un {@link User} et une {@link Api}
 *
 * @author jntakpe
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserApi extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "api_id", referencedColumnName = "id", updatable = false, nullable = false)
    private Api api;

    @ManyToOne(optional = false)
    @JoinColumn(name = "authority_name", referencedColumnName = "name", updatable = false, nullable = false)
    private Authority authority;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user_login", user.getLogin())
                .append("api_name", api.getName())
                .append("authority", authority)
                .toString();
    }
}
