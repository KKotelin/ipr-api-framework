package model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HouseDto {
    private Integer id;
    private Integer floorCount;
    private BigDecimal price;
    private List<ParkingPlaceDto> parkingPlaces;
    private List<UserDto> lodgers;
}
