package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query(value="select distinct supplier.supplierid, contract, delivery_time, discount, guarantee, name, type " +
            "from supply_detail " +
            "         inner join supply on supply_detail.supply_supplyid = supply.supplyid " +
            "         inner join supplier on supplier.supplierid = supply.supplier_supplierid " +
            "where type = ?1 and detail_detailid = ?2 and quantity > ?3 and supply.delivery_date between ?4 and ?5",
            nativeQuery = true)
    List<Supplier> findSupplierByParam(@Param("type") Integer type, @Param("detailID") Integer detailId, @Param("quantity") Integer quantity,
                                       @Param("deliveryDateStart") Date deliveryDateStart, @Param("deliveryDateEnd") Date deliveryDateEnd);

    @Query(value="select trunc(cast(sum( " +
            "    case when s.supplier_supplierid = ?1 and s.delivery_date between ?2 and ?3 " +
            "    then (sd.quantity * p.price) " +
            "    else 0 end) as numeric) /sum(d.price * pd.quantity)*100,2) " +
            "from supplier " +
            "inner join supply s on supplier.supplierid = s.supplier_supplierid " +
            "inner join supply_detail sd on sd.supply_supplyid = s.supplyid " +
            "inner join price_list p on supplier.supplierid = p.supplier_supplierid " +
            "inner join detail d on sd.detail_detailid = d.detailid " +
            "inner join purchase_detail pd on d.detailid = pd.detail_detailid",
            nativeQuery = true)
    Double findSpecificSupplierGoodsShare(@Param("supplierID") Integer supplierID,
                                                  @Param("deliveryDateStart") Date deliveryDateStart,
                                                  @Param("deliveryDateEnd") Date deliveryDateEnd);

}
