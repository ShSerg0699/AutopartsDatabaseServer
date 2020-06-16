package nsu.shserg.AutopartsDatabaseServer.entity;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplyID;

    private Date deliveryDate;

    private Integer marriageRate;

    private Integer customsClearance;

    @OneToOne(cascade = CascadeType.ALL)
    private Supplier supplier;

}
