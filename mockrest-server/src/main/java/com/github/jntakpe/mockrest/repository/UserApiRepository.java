package com.github.jntakpe.mockrest.repository;

import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.domain.UserApi;

/**
 * Publication des méthodes de gestion de l'entité {@link com.github.jntakpe.mockrest.domain.UserApi}
 *
 * @author jntakpe
 */
public interface UserApiRepository extends GenericRepository<UserApi> {

    UserApi findByApi(Api api);

}
