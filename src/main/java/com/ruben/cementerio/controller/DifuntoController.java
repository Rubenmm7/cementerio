package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.entity.Difunto;
import com.ruben.cementerio.service.DifuntoService;

@RestController
@RequestMapping("/api/difuntos")
@CrossOrigin(origins = "http://localhost:4200")
public class DifuntoController {

    private final DifuntoService difuntoService;

    public DifuntoController(DifuntoService difuntoService) {
        this.difuntoService = difuntoService;
    }

    @GetMapping
    public List<Difunto> listar() {
        return difuntoService.listarTodos();
    }
    
    @GetMapping("/buscar")
    public List<Difunto> buscar(@RequestParam String query) {
        // Busca por nombre o apellido
        return difuntoService.buscar(query, query);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Difunto> obtener(@PathVariable Long id) {
        return ResponseEntity.of(difuntoService.obtenerPorId(id));
    }

    @PostMapping
    public Difunto crear(@RequestBody Difunto difunto) {
        return difuntoService.guardar(difunto);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        difuntoService.eliminar(id);
    }
}