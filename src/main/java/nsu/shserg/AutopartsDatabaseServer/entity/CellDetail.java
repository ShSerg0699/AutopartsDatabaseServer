package nsu.shserg.AutopartsDatabaseServer.entity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class CellDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cellDetailID;

    @ManyToOne
    private Detail detail;

    @ManyToOne
    private CellInStock cellInStock;

    private int quantity;

    private Date appearanceDate;
}
