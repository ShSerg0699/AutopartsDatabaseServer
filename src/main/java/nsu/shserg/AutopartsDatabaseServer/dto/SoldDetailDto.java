package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SoldDetailDto {
    private Integer detailID;
    private String name;
    private Integer price;
}
