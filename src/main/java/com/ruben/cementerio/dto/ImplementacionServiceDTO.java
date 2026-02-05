package com.ruben.cementerio.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImplementacionServiceDTO {
    private Integer id;
    private Date fecha_realizacion;
    private String foto;
    private Integer facturaId;
    private Integer parcelaId;
    private Integer servicioId;
}
