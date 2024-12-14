package com.banco.comercio.proveedores.bean.request;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
