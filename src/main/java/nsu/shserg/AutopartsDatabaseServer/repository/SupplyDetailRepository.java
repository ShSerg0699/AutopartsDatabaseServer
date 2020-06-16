package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.dto.TopDetailDto;
import nsu.shserg.AutopartsDatabaseServer.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDetailRepository extends JpaRepository<SupplyDetail, Integer> {
    List<SupplyDetail> findAllByDetail(Detail detail);

    List<SupplyDetail> findAllBySupply(Supply supply);

    @Query(value = "select new nsu.shserg.AutopartsDatabaseServer.dto.TopDetailDto(s.detail.detailID, sum(s.quantity)) " +
            "from SupplyDetail s " +
            "group by s.detail.detailID " +
            "order by sum(s.quantity) desc")
    Page<TopDetailDto> findTopDetailInSupply(Pageable pageable);
}
