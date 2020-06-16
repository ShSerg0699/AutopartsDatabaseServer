package nsu.shserg.AutopartsDatabaseServer.entity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierID;

    private String name;

    private Integer type;

    private String deliveryTime;

    private String guarantee;

    private Long contract;

    private Integer discount;
}
