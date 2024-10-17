package com.servicejob.apiServiceJob.service;

import com.servicejob.apiServiceJob.model.dto.ClienteDto;
import com.servicejob.apiServiceJob.model.entity.Cliente;

import java.util.List;

public interface IServiceCliente {

    List<Cliente> listAll();
    Cliente save(ClienteDto cliente);

    Cliente findById(Integer id);

    void delete(Cliente cliente);

    boolean existsById(Integer id);
}
