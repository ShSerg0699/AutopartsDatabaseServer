package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchDetailInStockDto {
    private Integer detailID;
    private String name;
    private Integer cellID;
    private Integer space;
}
