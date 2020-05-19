package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.Data;
import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Date;
import java.util.List;

@Data
public class PurchaseDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseID;
    private Date purchaseDate;
    private Buyer buyer;
    private List<DetailDto> detailList;
}
