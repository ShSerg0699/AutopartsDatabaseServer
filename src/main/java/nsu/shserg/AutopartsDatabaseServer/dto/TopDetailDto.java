package nsu.shserg.AutopartsDatabaseServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopDetailDto {
    private Integer detailID;
    private Long quantity;
}
