package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.entity.Cementerio;
import com.ruben.cementerio.service.CementerioService;

@RestController
@RequestMapping("/api/cementerios")
@CrossOrigin(origins = "http://localhost:4200")
public class CementerioController {

    private final CementerioService cementerioService;

    public CementerioController(CementerioService cementerioService) {
        this.cementerioService = cementerioService;
    }

    @GetMapping
    public List<Cementerio> listar() {
        return cementerioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cementerio> obtener(@PathVariable Long id) {
        return ResponseEntity.of(cementerioService.obtenerPorId(id));
    }

    @PostMapping
    public Cementerio crear(@RequestBody Cementerio cementerio) {
        return cementerioService.guardar(cementerio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cementerio> actualizar(@PathVariable Long id, @RequestBody Cementerio cementerio) {
        return cementerioService.actualizar(id, cementerio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        cementerioService.eliminar(id);
    }
}