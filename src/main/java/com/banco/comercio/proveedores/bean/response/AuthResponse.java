package com.banco.comercio.proveedores.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    @JsonIgnore
    private HttpStatus status;

    public AuthResponse(String token){
        this.token = token;
    }
}
