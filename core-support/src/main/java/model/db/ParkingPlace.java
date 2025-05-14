package model.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingPlace {
    private Integer id;
    private Boolean isWarm;
    private Boolean isCovered;
    private Integer placesCount;
    private Integer houseId;
}
