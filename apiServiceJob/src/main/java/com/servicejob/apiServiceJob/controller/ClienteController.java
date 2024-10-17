package com.servicejob.apiServiceJob.controller;

import com.servicejob.apiServiceJob.model.dto.ClienteDto;
import com.servicejob.apiServiceJob.model.entity.Cliente;
import com.servicejob.apiServiceJob.model.payload.MessageResponse;
import com.servicejob.apiServiceJob.service.IServiceCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IServiceCliente clienteService;

    @GetMapping("cliente")
    public ResponseEntity<?> showAll() {
      List<Cliente> getList = clienteService.listAll();
        if (getList == null){
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("No hay datos")
                    .object(null).build(),
                    HttpStatus.OK);

        }
        return new ResponseEntity<>(MessageResponse.builder().message("").object(getList).build(), HttpStatus.OK);


    }
    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);


            return new ResponseEntity<>(MessageResponse.builder().message("Se guardo correctamente").object(ClienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .correo(clienteSave.getCorreo())
                    .build()).build(), HttpStatus.CREATED);
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(MessageResponse.builder().message("Error en guardado").object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id) {
        Cliente clienteUpdate = null;

        if (clienteService.existsById(id)){
            clienteDto.setIdCliente(id);
            clienteUpdate = clienteService.save(clienteDto);
            return new ResponseEntity<>(MessageResponse.builder().message("Se actualizo correctamente").object(ClienteDto.builder()
                    .idCliente(clienteUpdate.getIdCliente())
                    .apellido(clienteUpdate.getApellido())
                    .nombre(clienteUpdate.getNombre()).
                    fechaRegistro(clienteUpdate.getFechaRegistro())
                    .correo(clienteUpdate.getCorreo()).build()).build(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(MessageResponse.builder().message("El registro no existe").object(null).build(), HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(ex.getMessage())
                    .object(null).build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null){
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Error en consulta")
                    .object(null).build(),
                    HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(MessageResponse.builder().message("").object(ClienteDto.builder()
                .idCliente(cliente.getIdCliente())
                .apellido(cliente.getApellido())
                .nombre(cliente.getNombre()).
                fechaRegistro(cliente.getFechaRegistro())
                .correo(cliente.getCorreo()).build()).build(), HttpStatus.OK);


    }
}
