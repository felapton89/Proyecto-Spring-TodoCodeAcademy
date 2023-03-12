package com.example.demo.controller;

import com.example.demo.model.entities.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping("/crear")
    public ResponseEntity<?> save(@RequestBody Producto producto) {
        try {
            return ResponseEntity.ok(service.save(producto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProductos() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{codigo_producto}")
    public ResponseEntity<?> getByCodigoProducto(@PathVariable(name = "codigo_producto") Long codigoProducto) {
        try {
            return ResponseEntity.ok(service.findById(codigoProducto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/editar/{codigo_producto}")
    public ResponseEntity<?> edit(@PathVariable(name = "codigo_producto") Long codigoProducto,
                                  @RequestBody Producto producto) {
        try {
            return ResponseEntity.ok(service.edit(codigoProducto, producto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{codigo_producto}")
    public ResponseEntity<?> delete(@PathVariable(name = "codigo_producto") Long codigoProducto) {
        try {
            service.delete(codigoProducto);
            return ResponseEntity.ok("Producto Eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/falta_stock")
    public ResponseEntity<?> lowStock(){
        try {
            return ResponseEntity.ok(service.findLowStock());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
