package com.banco.comercio.proveedores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banco.comercio.proveedores.bean.response.CambioEstadoResponse;
import com.banco.comercio.proveedores.fitbank.beans.response.ProveedoresLoan;
import com.banco.comercio.proveedores.service.SolicitudProveedoresService;
import com.banco.comercio.proveedores.util.ApplicationConstans;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequestMapping(value = "/api/proveedores")
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ProveedoresController {
    @Autowired
    SolicitudProveedoresService service;
    @GetMapping()
    public ResponseEntity<Object> getProveedoresAll() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

//    @PostMapping("/byDocNom")
//    public ResponseEntity<Object> getProveedoresByDocOrNom(@RequestBody ProveedoresLoan request) throws Exception {
//        return ResponseEntity.status(HttpStatus.OK).body(service.getByDocOrNom(request));
//    }

    @PostMapping("/byDocNom")
    public ResponseEntity<Object> getProveedoresByRazonOrDoc(@RequestParam(required = false) Optional<String> numDoc,
                                                           @RequestParam(required = false) Optional<String> razon) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.getProveedoresByRazonOrDoc(razon, numDoc));
    }

    @PostMapping()
    public ResponseEntity<Object> saveProveedor(@RequestBody ProveedoresLoan request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.saveProveedor(request));
    }

}
