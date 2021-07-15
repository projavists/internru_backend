package ru.intern.rest;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kir
 * Created on 07.07.2021
 */
@RestController
@Api(value = "/api", description = "swagger example")
public class Example {

    @GetMapping
    public String get(){
        return "Hello World!";
    }
}
