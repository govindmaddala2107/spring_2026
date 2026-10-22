package com.gomad.ecom.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomApiResponse {

    private String message;
    private boolean status;
    private HttpStatus code;
}
