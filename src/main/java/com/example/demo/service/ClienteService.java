package com.example.demo.service;

import com.example.demo.model.entities.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();

    Cliente findById(Long id);

    Cliente save(Cliente cliente);

    Cliente edit(Long id, Cliente cliente);

    void delete(Long id);

}
