package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.entity.Difunto;
import com.ruben.cementerio.service.DifuntoService;

@RestController
@RequestMapping("/api/public/busqueda")
@CrossOrigin(origins = "http://localhost:4200")
public class BusquedaPublicaController {

    private final DifuntoService difuntoService;

    public BusquedaPublicaController(DifuntoService difuntoService) {
        this.difuntoService = difuntoService;
    }

    // Endpoint público para buscar difuntos por nombre/apellido
    // Ejemplo: /api/public/busqueda?q=Garcia
    @GetMapping
    public List<Difunto> buscarDifuntos(@RequestParam("q") String query) {
        return difuntoService.buscar(query, query);
    }
}