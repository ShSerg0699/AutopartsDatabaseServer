package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DetailInformationDto {
    private Integer supplierID;
    private String supplierName;
    private Integer price;
    private String deliveryTime;
}
