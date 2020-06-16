package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class InventoryListDto {
    private Integer detailID;
    private Integer cellID;
    private Date appearanceDate;
    private Date purchaseDate;
}
