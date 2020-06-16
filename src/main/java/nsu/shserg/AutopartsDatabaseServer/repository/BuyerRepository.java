package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
    @Query(value = "select distinct buyer " +
            "from PurchaseDetail purchase_detail " +
            "inner join Purchase purchase on purchase_detail.purchase.purchaseID = purchase.purchaseID " +
            "inner join Buyer buyer on purchase.buyer.buyerID = buyer.buyerID " +
            "where purchase_detail.detail.detailID = :detailID and (purchase_detail.quantity > :quantity " +
            "or purchase.purchaseDate between :purchaseDateStart and :purchaseDateEnd)")
    List<Buyer> findBuyerByParam(@Param("detailID") Integer detailID, @Param("quantity") Integer quantity,
                                 @Param("purchaseDateStart") Date purchaseDateStart, @Param("purchaseDateEnd") Date purchaseDateEnd);
}
