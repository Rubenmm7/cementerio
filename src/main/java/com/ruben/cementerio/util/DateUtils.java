package com.ruben.cementerio.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    private static final DateTimeFormatter FORMATO_ESPANOL = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatearFecha(LocalDate fecha) {
        if (fecha == null) return "";
        return fecha.format(FORMATO_ESPANOL);
    }
}