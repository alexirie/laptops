package com.example.demo.controllers;

import com.example.demo.entities.Laptop;
import com.example.demo.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    //atributos
    private LaptopRepository laptopRepository;

    //constructor
    public LaptopController(LaptopRepository laptopRepository){
        this.laptopRepository = laptopRepository;
    }

    //metodos
    @GetMapping("/")
    public String index(){
        return "Hola mundo, esta es la app laptops";
    }

    //buscar todos
    @GetMapping("/api/laptops")
    public List<Laptop> findAll(){

        log.info("Ejecutando metodo findAll...");
        return laptopRepository.findAll();
    }

    //buscar por id
    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){

        log.info("Ejecutando metodo findOneById...");

        Optional<Laptop> optional = laptopRepository.findById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        } else{
            return ResponseEntity.notFound().build();
        }

    }

    //create
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers){

        //mensaje del log
        log.info("Ejecutando el metodo create...");

        //mensaje con info de las cabeceras
        System.out.println(headers.get("User-Agent"));

        //guardamos el registro en la bd
        Laptop result = laptopRepository.save(laptop);

        //si va bien mandamos el mensaje de ok
        return ResponseEntity.ok(result);
    }

}
