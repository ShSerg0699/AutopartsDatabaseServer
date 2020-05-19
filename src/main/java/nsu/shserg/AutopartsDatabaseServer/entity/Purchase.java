package nsu.shserg.AutopartsDatabaseServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseID;

    private Date purchaseDate;
//    TODO
//    @OneToMany(fetch = FetchType.EAGER)
//    List<PurchaseDetail> detail;

    @OneToOne(cascade = CascadeType.ALL)
    private Buyer buyer;

}
