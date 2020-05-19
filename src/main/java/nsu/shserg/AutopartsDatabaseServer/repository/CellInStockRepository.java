package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.CellInStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellInStockRepository extends JpaRepository<CellInStock, Integer> {
}
