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
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:4200")
public class BusquedaPublicaController {

    private final DifuntoService difuntoService;

    public BusquedaPublicaController(DifuntoService difuntoService) {
        this.difuntoService = difuntoService;
    }

    // Endpoint: /api/public/buscar
    @GetMapping("/buscar")
    public List<Difunto> buscarDifuntos(
            @RequestParam(value = "ayuntamientoId", required = false) Long ayuntamientoId,
            @RequestParam(value = "cementerioId", required = false) Long cementerioId,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellido", required = false) String apellido
    ) {
        return difuntoService.buscarAvanzado(ayuntamientoId, cementerioId, nombre, apellido);
    }
}