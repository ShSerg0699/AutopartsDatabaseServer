package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.Data;

import java.util.List;

@Data
public class CellInStockDto {
    private Integer cellID;
    private List<DetailInCellDto> detailList;
    private int space;

}
