package br.com.pinalli.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    /**
     * HTTP Methods accepted by REST
     * GET - get information
     * POST - create information     
     * DELETE - delete information
     * PATCH - update information, one part fields/data   
     * PUT - update information
     */
    @GetMapping("/first")
    public String hello() {
        return "Hello World!";
    }
}
