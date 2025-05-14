package model.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Car {
    private Integer id;
    private String mark;
    private String model;
    private BigDecimal price;
    private Integer engineTypeId;
    private Integer userId;
}
