package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.dto.DetailSpeedSaleDto;
import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DetailRepository extends JpaRepository<Detail, Integer> {
    @Query(value = "select trunc(cast( sum(s.customs_clearance*sd.quantity) as numeric)/sum(detail.price * p.quantity)*100, 2) " +
            "from detail " +
            "inner join supply_detail sd on detail.detailid = sd.detail_detailid " +
            "inner join supply s on s.supplyid = sd.supply_supplyid " +
            "inner join purchase_detail p on detail.detailid = p.detail_detailid",
            nativeQuery= true)
    Double findOverhead();

    @Query(value= "select new nsu.shserg.AutopartsDatabaseServer.dto.DetailSpeedSaleDto(d.detailID, s.deliveryDate, p.purchaseDate) " +
            "from Detail d " +
            "inner join SupplyDetail sd on d.detailID = sd.detail.detailID " +
            "inner join Supply s on sd.supply.supplyID = s.supplyID " +
            "inner join PurchaseDetail pd on d.detailID = pd.detail.detailID " +
            "inner join Purchase p on pd.purchase.purchaseID = p.purchaseID " +
            "where p.purchaseDate > s.deliveryDate")
    List<DetailSpeedSaleDto> findGoodsSpeedSale();
}
