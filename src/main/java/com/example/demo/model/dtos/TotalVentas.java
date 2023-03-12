package com.example.demo.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TotalVentas {

    private LocalDate fechaVenta;

    private Integer cantidadDeVentas;

    private Double montoTotal;

}
