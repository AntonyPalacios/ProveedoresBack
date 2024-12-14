package com.banco.comercio.proveedores.controller;

import com.banco.comercio.proveedores.bean.request.AuthRequest;
import com.banco.comercio.proveedores.bean.response.AuthResponse;
import com.banco.comercio.proveedores.fitbank.beans.UserFitBank;
import com.banco.comercio.proveedores.jwt.JWTUtil;
import com.banco.comercio.proveedores.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JWTUtil jwtUtil;



    @PostMapping("/auth")
    public ResponseEntity<Object> login(@RequestBody AuthRequest loginRequest){
        AuthResponse response =  new AuthResponse();

        //HACER EL LOGIN
        UserFitBank userFitBank = userService.login(loginRequest.getUsername(),loginRequest.getPassword());
        if(userFitBank!=null){
            //crear el token y devolverlo
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            response.setToken(token);
            response.setStatus(HttpStatus.OK);
        }else{
            response.setMessage("USUARIO O PASSWORD INCORRECTO");
            response.setStatus(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(response.getStatus()).body(response);
//        UserDetails userDetails;
//        boolean emailExists = userService.verifyEmailExists(loginRequest.getUsername());
//        if(emailExists){
//            try {
//                authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//                userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
//            }catch (BadCredentialsException e){
//                int numTry = userService.getUserNumTries(loginRequest.getUsername());
//                response.setStatus(HttpStatus.BAD_REQUEST);
//                if(numTry == 0){
//                    response.setMessage("Usuario bloqueado. Póngase en contacto con el dueño del proceso para reactivar su usuario.");
//
//                }else{
//                    response.setMessage("Usuario o contraseña incorrectos. Intentos restantes " + numTry);
//                }
//                return  ResponseEntity.status(response.getStatus()).body(response);
//            }
//        }else{
//            response.setStatus(HttpStatus.BAD_REQUEST);
//            response.setMessage("Usuario o contraseña incorrectos.");
//            return  ResponseEntity.status(response.getStatus()).body(response);
//        }
//        final String token = jwtUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
//            Exception exception,
//            WebRequest request){
//        return buildErrorResponse(exception, "Error desconocido del sistema.", HttpStatus.INTERNAL_SERVER_ERROR, request
//        );
//    }
//
//    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpStatus httpStatus,
//                                                             WebRequest request) {
//        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
//    }
//
//    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, String message,
//                                                             HttpStatus httpStatus, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage());
//
//        return ResponseEntity.status(httpStatus).body(errorResponse);
//    }
}
