package nsu.shserg.AutopartsDatabaseServer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SupplyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplyDetailID;

    @ManyToOne
    private Detail detail;

    @ManyToOne
    private Supply supply;

    private int quantity;
}
