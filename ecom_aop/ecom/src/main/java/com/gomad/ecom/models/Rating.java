package com.gomad.ecom.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Double rate;
    private Integer count;
}
