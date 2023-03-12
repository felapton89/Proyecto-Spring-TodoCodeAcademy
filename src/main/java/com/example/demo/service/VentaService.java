package com.example.demo.service;

import com.example.demo.model.dtos.MayorVenta;
import com.example.demo.model.dtos.ProductosDeVenta;
import com.example.demo.model.dtos.TotalVentas;
import com.example.demo.model.entities.Producto;
import com.example.demo.model.entities.Venta;

import java.time.LocalDate;
import java.util.List;

public interface VentaService {

    List<Venta> findAll();

    Venta findById(Long id);

    Venta save(Venta venta);

    Venta edit(Long id, Venta venta);

    void delete(Long id);

    void validVenta(Venta venta);

    List<ProductosDeVenta> findProductosByVentaId(Long codigoVenta);

    TotalVentas findByFecha(String fecha);

    List<MayorVenta> findMayorVenta();
}


