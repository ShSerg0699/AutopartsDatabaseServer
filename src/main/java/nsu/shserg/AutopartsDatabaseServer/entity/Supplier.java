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

    private int type;

    private String deliveryTime;

    @Column(nullable=true)
    private String guarantee;

    @Column(nullable=true)
    private long contract;

    @Column(nullable=true)
    private int discount;
}
