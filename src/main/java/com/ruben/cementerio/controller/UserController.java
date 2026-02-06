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
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.service.UserService;

@RestController
@RequestMapping("/api/users") // O la ruta que prefieras
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> listar() {
        return userService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<User> crear(@RequestBody UserRequestDTO userRequestDTO) {
        // Ahora sí funciona porque el método existe en el servicio
        User nuevoUsuario = userService.crear(userRequestDTO);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtener(@PathVariable Long id) {
        return ResponseEntity.of(userService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        userService.eliminar(id);
    }
}