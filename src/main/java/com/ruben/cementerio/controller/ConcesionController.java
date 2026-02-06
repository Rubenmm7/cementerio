package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.entity.Concesion;
import com.ruben.cementerio.service.ConcesionService;

@RestController
@RequestMapping("/api/concesiones")
@CrossOrigin(origins = "http://localhost:4200")
public class ConcesionController {

    private final ConcesionService concesionService;

    public ConcesionController(ConcesionService concesionService) {
        this.concesionService = concesionService;
    }

    @GetMapping
    public List<Concesion> listarActivas() {
        return concesionService.listarActivas();
    }

    @PostMapping
    public Concesion crear(@RequestBody Concesion concesion) {
        return concesionService.guardar(concesion);
    }
    
    @DeleteMapping("/{id}")
    public void cancelar(@PathVariable Long id) {
        concesionService.cancelarConcesion(id);
    }
}