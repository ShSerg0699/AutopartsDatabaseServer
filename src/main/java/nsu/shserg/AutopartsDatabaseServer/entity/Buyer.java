package nsu.shserg.AutopartsDatabaseServer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buyerID;

    private String name;
}
