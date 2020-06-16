package nsu.shserg.AutopartsDatabaseServer.entity;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detailID;

    private String name;

    private Integer price;

    private Integer size;
}
