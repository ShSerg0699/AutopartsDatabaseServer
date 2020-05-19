package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDetailRepository extends JpaRepository<SupplyDetail, Integer> {
    List<SupplyDetail> findAllByDetail(Detail detail);

    List<SupplyDetail> findAllBySupply(Supply supply);
}
