package com.servicejob.apiServiceJob.model.dao;

import com.servicejob.apiServiceJob.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente,Integer> {

}
