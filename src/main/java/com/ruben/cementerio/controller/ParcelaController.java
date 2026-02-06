package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.entity.Parcela;
import com.ruben.cementerio.service.ParcelaService;

@RestController
@RequestMapping("/api/parcelas")
@CrossOrigin(origins = "http://localhost:4200")
public class ParcelaController {

    private final ParcelaService parcelaService;

    public ParcelaController(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    @GetMapping("/zona/{id}")
    public List<Parcela> listarPorZona(@PathVariable Long id) {
        return parcelaService.listarPorZona(id);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Parcela> obtener(@PathVariable Long id) {
        return ResponseEntity.of(parcelaService.obtenerPorId(id));
    }

    @PostMapping
    public Parcela crear(@RequestBody Parcela parcela) {
        return parcelaService.guardar(parcela);
    }
}