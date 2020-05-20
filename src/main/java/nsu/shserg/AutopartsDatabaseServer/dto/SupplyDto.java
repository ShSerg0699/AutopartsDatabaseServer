package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.Data;
import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import nsu.shserg.AutopartsDatabaseServer.entity.Supplier;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Date;
import java.util.List;

@Data
public class SupplyDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplyID;
    private Date deliveryDate;
    private Supplier supplier;
    private int marriageRate;
    private int customsClearance;
    private List<DetailDto> detailList;
}
