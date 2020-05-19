package nsu.shserg.AutopartsDatabaseServer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PurchaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseDetailID;

    @ManyToOne
    private Detail detail;

    @ManyToOne
    private Purchase purchase;

    private int quantity;

}
