package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import nsu.shserg.AutopartsDatabaseServer.entity.Purchase;
import nsu.shserg.AutopartsDatabaseServer.entity.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
    List<PurchaseDetail> findAllByDetail(Detail detail);

    List<PurchaseDetail> findAllByPurchase(Purchase purchase);
}
