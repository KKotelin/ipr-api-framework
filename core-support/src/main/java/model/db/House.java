package model.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class House {
    private Integer id;
    private Integer floorCount;
    private BigDecimal price;
}
