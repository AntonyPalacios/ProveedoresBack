package com.banco.comercio.proveedores.controller;

import com.banco.comercio.proveedores.bean.request.SolicitudProveedoresRequest;
import com.banco.comercio.proveedores.bean.response.CambioEstadoResponse;
import com.banco.comercio.proveedores.bean.response.SolicitudProveedoresResponse;
import com.banco.comercio.proveedores.service.SolicitudProveedoresService;
import com.banco.comercio.proveedores.util.ApplicationConstans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping(value = "/api/solicitudProveedores")
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class SolicitudProveedoresController {
    @Autowired
    SolicitudProveedoresService service;



    @GetMapping("/{id}")
    public ResponseEntity<SolicitudProveedoresResponse> getSolicitudProveedoresById(@PathVariable  String id)  {
        SolicitudProveedoresResponse respuesta = service.getById(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<CambioEstadoResponse> postSolicitudProveedores(@RequestBody SolicitudProveedoresRequest request) throws Exception {
        CambioEstadoResponse prestates = service.saveSolicitudProveedor(request);
        prestates.setCode(ApplicationConstans.RESULT_OK);
        prestates.setStatus(ApplicationConstans.RESULT_OK);
        prestates.setMessage(ApplicationConstans.MESSAGE_RESULT_OK);

        return new ResponseEntity<>(prestates, HttpStatus.CREATED);
    }


    /*@PutMapping()
    public ResponseEntity<SolicitudProveedoresResponse> putSolicitudProveedores(@RequestBody SolicitudProveedoresRequest request)  {
        SolicitudProveedoresResponse respuesta = service.update(request);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }*/
}
