package com.ruben.cementerio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")  // Permitir solicitudes desde Angular
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear usuario
    @PostMapping
    public UserResponseDTO crear(@RequestBody UserRequestDTO dto) { // @RequestBody indica que los datos vienen en el cuerpo de la petición HTTP y Spring los transforma en un objeto Java
        return userService.crear(dto);
    }

    // Listar usuarios
    @GetMapping
    public List<UserResponseDTO> listar() {
        return userService.listar();
    }

    
    
}
