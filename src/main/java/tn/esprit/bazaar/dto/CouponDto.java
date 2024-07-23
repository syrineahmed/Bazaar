package tn.bazaar.dto;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
public class CouponDto {
    private Long id;

    private String code;

    private Long discount;

    private Date expirationDate;
}
