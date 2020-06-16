package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.dto.InventoryListDto;
import nsu.shserg.AutopartsDatabaseServer.dto.SearchDetailInStockDto;
import nsu.shserg.AutopartsDatabaseServer.dto.StockpiledDetailDto;
import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import nsu.shserg.AutopartsDatabaseServer.entity.CellDetail;
import nsu.shserg.AutopartsDatabaseServer.entity.CellInStock;
import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CellDetailRepository  extends JpaRepository<CellDetail, Integer> {
    List<CellDetail> findAllByDetail(Detail detail);

    List<CellDetail> findAllByCellInStock(CellInStock cell);

    @Query(value = "select new  nsu.shserg.AutopartsDatabaseServer.dto.SearchDetailInStockDto(cd.detail.detailID, d.name, cd.cellInStock.cellID, c.space) " +
            "from CellDetail cd " +
            "inner join Detail d on cd.detail.detailID = d.detailID " +
            "inner join CellInStock c on cd.cellInStock.cellID = c.cellID " +
            "order by cd.detail.detailID")
    List<SearchDetailInStockDto> findDetailInStock();

    @Query(value = "select distinct new nsu.shserg.AutopartsDatabaseServer.dto.StockpiledDetailDto(d.detailID, d.name) " +
            "from CellDetail cd " +
            "inner join Detail d on cd.detail.detailID = d.detailID " +
            "where cd.detail.detailID in ( " +
            "    select pd.detail.detailID " +
            "    from PurchaseDetail pd" +
            "    inner join Purchase p on pd.purchase.purchaseID = p.purchaseID " +
            "    where p.purchaseDate between ?1 and ?2)")
    List<StockpiledDetailDto> findStockpiledDetail(@Param("purchaseDateStart") Date purchaseDateStart, @Param("purchaseDateEnd") Date purchaseDateEnd);

    @Query(value = "select new nsu.shserg.AutopartsDatabaseServer.dto.InventoryListDto(cd.detail.detailID, cd.cellInStock.cellID, cd.appearanceDate, p.purchaseDate) " +
            "from CellDetail cd " +
            "inner join Detail d on cd.detail.detailID = d.detailID " +
            "inner join PurchaseDetail pd on d.detailID = pd.detail.detailID " +
            "inner join Purchase p on pd.purchase.purchaseID = p.purchaseID " +
            "where cd.appearanceDate between ?1 and ?2 " +
            "order by cd.detail.detailID")
    List<InventoryListDto> findInventoryList(@Param("appearanceDateStart") Date appearanceDateStart, @Param("appearanceDateEnd") Date appearanceDateEnd);

    @Query(value = "select distinct c " +
            "from CellInStock c " +
            "where c.cellID not in( " +
            "    select cd.cellInStock.cellID " +
            "    from CellDetail cd " +
            ")")
    List<CellInStock> findEmptyCell();
}
