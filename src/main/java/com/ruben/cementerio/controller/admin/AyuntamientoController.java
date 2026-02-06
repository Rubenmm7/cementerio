package com.ruben.cementerio.controller.admin;

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

import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.service.AyuntamientoService;

@RestController
@RequestMapping("/api/admin/ayuntamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class AyuntamientoController {

    private final AyuntamientoService ayuntamientoService;

    public AyuntamientoController(AyuntamientoService ayuntamientoService) {
        this.ayuntamientoService = ayuntamientoService;
    }

    @GetMapping
    public List<Ayuntamiento> listar() {
        return ayuntamientoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ayuntamiento> obtener(@PathVariable Long id) {
        return ResponseEntity.of(ayuntamientoService.obtenerPorId(id));
    }

    @PostMapping
    public Ayuntamiento crear(@RequestBody Ayuntamiento ayuntamiento) {
        return ayuntamientoService.guardar(ayuntamiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ayuntamiento> actualizar(@PathVariable Long id, @RequestBody Ayuntamiento ayuntamiento) {
        return ayuntamientoService.obtenerPorId(id)
                .map(existente -> {
                    existente.setNombre(ayuntamiento.getNombre());
                    existente.setDireccion(ayuntamiento.getDireccion());
                    existente.setTelefono(ayuntamiento.getTelefono());
                    return ResponseEntity.ok(ayuntamientoService.guardar(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ayuntamientoService.eliminar(id);
    }
}