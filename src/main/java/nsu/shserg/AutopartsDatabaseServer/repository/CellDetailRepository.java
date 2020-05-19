package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.CellDetail;
import nsu.shserg.AutopartsDatabaseServer.entity.CellInStock;
import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellDetailRepository  extends JpaRepository<CellDetail, Integer> {
    List<CellDetail> findAllByDetail(Detail detail);

    List<CellDetail> findAllByCellInStock(CellInStock cell);
}
