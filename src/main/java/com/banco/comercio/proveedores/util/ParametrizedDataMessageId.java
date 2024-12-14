package com.banco.comercio.proveedores.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParametrizedDataMessageId<T> {
    private T data;
    private String msgId;
}
