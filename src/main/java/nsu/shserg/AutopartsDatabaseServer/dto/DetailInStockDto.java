package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class DetailInStockDto {
    private Integer detailID;
    private int size;
    private int quantity;
    private Date appearanceDate;
}
