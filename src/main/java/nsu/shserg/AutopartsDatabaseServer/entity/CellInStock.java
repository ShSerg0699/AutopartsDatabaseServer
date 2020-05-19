package nsu.shserg.AutopartsDatabaseServer.entity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class CellInStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cellID;

    private int space;

}
