package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CashReportDto {
    private Date purchaseDate;
    private Integer detailID;
    private String name;
    private Integer quantity;
    private Integer buyerId;
}
