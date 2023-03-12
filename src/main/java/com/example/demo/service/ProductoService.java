package com.example.demo.service;

import com.example.demo.model.entities.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> findAll();

    Producto findById(Long id);

    Producto save(Producto producto);

    Producto edit(Long id, Producto producto);

    void delete(Long id);

    List<Producto> findLowStock();

}
