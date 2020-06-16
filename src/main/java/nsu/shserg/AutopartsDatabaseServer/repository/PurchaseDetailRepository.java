package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.dto.CashReportDto;
import nsu.shserg.AutopartsDatabaseServer.dto.SoldDetailDto;
import nsu.shserg.AutopartsDatabaseServer.dto.TopDetailDto;
import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import nsu.shserg.AutopartsDatabaseServer.entity.Purchase;
import nsu.shserg.AutopartsDatabaseServer.entity.PurchaseDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
    List<PurchaseDetail> findAllByDetail(Detail detail);

    List<PurchaseDetail> findAllByPurchase(Purchase purchase);

    @Query(value = "select new nsu.shserg.AutopartsDatabaseServer.dto.TopDetailDto(p.detail.detailID, sum(p.quantity)) " +
            "from PurchaseDetail p " +
            "group by p.detail.detailID " +
            "order by sum(p.quantity) desc")
    Page<TopDetailDto> findTopDetailInPurchase(Pageable pageable);

    @Query(value = "select new nsu.shserg.AutopartsDatabaseServer.dto.SoldDetailDto(d.detailID, d.name, d.price) " +
            "from PurchaseDetail pd " +
            "    inner join Detail d on pd.detail.detailID = d.detailID " +
            "inner join Purchase p on pd.purchase.purchaseID = p.purchaseID " +
            "where p.purchaseDate = ?1")
    List<SoldDetailDto> findSoldDetailByDate(@Param("purchaseDate") Date purchaseDate);

    @Query(value = "select new nsu.shserg.AutopartsDatabaseServer.dto.CashReportDto(p.purchaseDate, pd.detail.detailID, d.name, pd.quantity, p.buyer.buyerID) " +
            "from PurchaseDetail pd " +
            "inner join Detail d on pd.detail.detailID = d.detailID " +
            "inner join Purchase p on pd.purchase.purchaseID = p.purchaseID " +
            "where p.purchaseDate between ?1 and ?2 " +
            "order by p.purchaseDate")
    List<CashReportDto> findCashReport(@Param("purchaseDateStart") Date purchaseDateStart, @Param("purchaseDateEnd") Date purchaseDateEnd);
}
