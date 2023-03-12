package com.example.demo.service;

import com.example.demo.model.entities.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = repository.findAll();
        if (clientes.isEmpty()) throw new RuntimeException("No hay clientes en la base de datos...");
        return clientes;
    }

    @Override
    public Cliente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("No hay cliente con id: " + id)
                );
    }

    @Override
    public Cliente save(Cliente cliente) {
        try {
            return repository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al querer guardar el cliente");
        }
    }

    @Override
    public Cliente edit(Long id, Cliente cliente) {
        try {
            Cliente original = this.findById(id);
            cliente.setIdCliente(original.getIdCliente());
            return repository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al querer editar el cliente");
        }
    }


    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al querer eliminar el cliente con id: " + id);
        }
    }
}
