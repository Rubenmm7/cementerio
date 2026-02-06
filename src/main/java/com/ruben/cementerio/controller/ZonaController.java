package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.entity.Zona;
import com.ruben.cementerio.service.ZonaService;

@RestController
@RequestMapping("/api/zonas")
@CrossOrigin(origins = "http://localhost:4200")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping("/cementerio/{id}")
    public List<Zona> listarPorCementerio(@PathVariable Long id) {
        return zonaService.listarPorCementerio(id);
    }

    @PostMapping
    public Zona crear(@RequestBody Zona zona) {
        return zonaService.guardar(zona);
    }
}