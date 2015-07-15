package com.github.jntakpe.mockrest.web;

import com.github.jntakpe.mockrest.config.UrlConstants;
import com.github.jntakpe.mockrest.domain.Api;
import com.github.jntakpe.mockrest.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Ressource REST représentant une API
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(UrlConstants.API)
public class ApiResource {

    private ApiService apiService;

    @Autowired
    public ApiResource(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Api> create(@RequestBody Api api) {
        if (apiService.findByName(api.getName()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(apiService.create(api), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        apiService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
