package com.example.demo.service;

import com.example.demo.model.entities.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Override
    public List<Producto> findAll() {
        List<Producto> productos = repository.findAll();
        if (productos.isEmpty()) throw new NoSuchElementException("No hay productos registrados...");
        return productos;
    }

    @Override
    public Producto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("No hay producto con id: " + id)
                );
    }

    @Override
    public Producto save(Producto producto) {
        try {
            if (producto.getCantidadDisponible() < 0) {
                throw new NumberFormatException("No puede colocar valores negativos en stock");
            }
            return repository.save(producto);
        } catch (NumberFormatException n) {
            throw n;
        } catch (Exception e) {
            throw new RuntimeException("Error al querer guardar un producto...");
        }
    }

    @Override
    public Producto edit(Long id, Producto producto) {
        Producto original = repository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("No hay producto con id: " + id)
                );
        try {
            producto.setCodigoProducto(original.getCodigoProducto());
            return repository.save(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al querer editar un producto...");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NoSuchElementException("Error al querer eliminar producto con id: " + id);
        }
    }

    @Override
    public List<Producto> findLowStock() {
        List<Producto> productos = this.findAll();
        List<Producto> lowStock = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getCantidadDisponible() < 5) {
                lowStock.add(p);
            }
        }
        if (lowStock.isEmpty()) {
            throw new RuntimeException("No hay productos con stock bajo");
        }
        return lowStock;
    }
}
