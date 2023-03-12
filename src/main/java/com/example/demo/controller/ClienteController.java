package com.example.demo.controller;

import com.example.demo.model.entities.Cliente;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping("/crear")
    public ResponseEntity<?> save(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.save(cliente));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllClientes() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity<?> getByIdCliente(@PathVariable(name = "id_cliente") Long idCliente) {
        try {
            return ResponseEntity.ok(service.findById(idCliente));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/editar/{id_cliente}")
    public ResponseEntity<?> edit(@PathVariable(name = "id_cliente") Long idCliente,
                                  @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.edit(idCliente, cliente));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id_cliente}")
    public ResponseEntity<?> delete(@PathVariable(name = "id_cliente") Long idCliente) {
        try {
            service.delete(idCliente);
            return ResponseEntity.ok("Cliente eliminado con éxito...");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
