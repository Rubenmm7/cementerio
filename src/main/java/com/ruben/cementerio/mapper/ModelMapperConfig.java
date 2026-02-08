package com.ruben.cementerio.mapper;

import java.util.Set;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.TipoRol;
import com.ruben.cementerio.entity.User;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración para mapear de User (Set<Rol>) a UserResponseDTO (TipoRol)
        Converter<Set<Rol>, TipoRol> rolesToTipoRolConverter = ctx -> {
            if (ctx.getSource() == null || ctx.getSource().isEmpty()) {
                return null;
            }
            return ctx.getSource().stream().findFirst().map(Rol::getTipo).orElse(null);
        };
        modelMapper.typeMap(User.class, UserResponseDTO.class).addMappings(mapper -> mapper.using(rolesToTipoRolConverter).map(User::getRoles, UserResponseDTO::setRole));
        return modelMapper;
    }
}
