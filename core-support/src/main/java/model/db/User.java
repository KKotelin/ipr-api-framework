package model.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private Integer age;
    private String firstName;
    private BigDecimal money;
    private String secondName;
    private Boolean sex;
    private Integer houseId;
}
