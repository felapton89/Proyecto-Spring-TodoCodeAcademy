package com.example.demo.controller;

import com.example.demo.model.entities.Venta;
import com.example.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService service;

    @PostMapping("/crear")
    public ResponseEntity<?> save(@RequestBody Venta venta) {
        try {
            if (venta.getFechaVenta() == null) venta.setFechaVenta(LocalDate.now());
            return ResponseEntity.ok(service.save(venta));
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

    @GetMapping("/{codigo_venta}")
    public ResponseEntity<?> getByCodigoProducto(@PathVariable(name = "codigo_venta") Long codigoVenta) {
        try {
            return ResponseEntity.ok(service.findById(codigoVenta));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/editar/{codigo_venta}")
    public ResponseEntity<?> edit(@PathVariable(name = "codigo_venta") Long codigoVenta,
                                  @RequestBody Venta venta) {
        try {
            return ResponseEntity.ok(service.edit(codigoVenta, venta));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{codigo_venta}")
    public ResponseEntity<?> delete(@PathVariable(name = "codigo_venta") Long codigoVenta) {
        try {
            service.delete(codigoVenta);
            return ResponseEntity.ok("Venta Eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/productos/{codigo_venta}")
    public ResponseEntity<?> getProductosByVentaId(@PathVariable(name = "codigo_venta") Long codigoVenta) {
        try {
            return ResponseEntity.ok(service.findProductosByVentaId(codigoVenta));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/fecha/{fecha_venta}")
    public ResponseEntity<?> getVentasByFecha(@PathVariable("fecha_venta") String fecha) {
        try {
            return ResponseEntity.ok(service.findByFecha(fecha));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/mayor_venta")
    public ResponseEntity<?> getMayorVenta() {
        try {
            return ResponseEntity.ok(service.findMayorVenta());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
