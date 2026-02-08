package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruben.cementerio.dto.CementerioDTO;
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
    public ResponseEntity<Cementerio> crear(@RequestBody CementerioDTO dto) {
        Cementerio nuevo = cementerioService.crear(dto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cementerio> actualizar(@PathVariable Long id, @RequestBody CementerioDTO dto) {
        return cementerioService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        cementerioService.eliminar(id);
    }
}