package com.github.jntakpe.mockrest.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jntakpe
 */
@RestController
@RequestMapping("/")
public class TestResource {

    @RequestMapping(method = RequestMethod.GET)
    public String hello(String name) {
        return "Hello " + name;
    }
}
