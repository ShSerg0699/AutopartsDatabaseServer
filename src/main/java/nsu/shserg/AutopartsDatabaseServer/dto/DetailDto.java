package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.Data;

@Data
public class DetailDto {
    private Integer detailID;
    private String name;
    private int price;
    private int quantity;
}
