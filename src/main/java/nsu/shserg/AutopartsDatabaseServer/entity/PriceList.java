package nsu.shserg.AutopartsDatabaseServer.entity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceListID;

    @ManyToOne
    private Detail detail;

    @ManyToOne
    private Supplier supplier;

    private Integer price;
}
