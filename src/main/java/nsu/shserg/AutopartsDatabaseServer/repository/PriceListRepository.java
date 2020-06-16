package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.dto.DetailInformationDto;
import nsu.shserg.AutopartsDatabaseServer.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Integer> {
    List<PriceList> findAllByDetail(Detail detail);

    List<PriceList> findAllBySupplier(Supplier supplier);

    @Query(value = "select new nsu.shserg.AutopartsDatabaseServer.dto.DetailInformationDto(p.supplier.supplierID, s.name, p.price, s.deliveryTime) " +
            "from PriceList p " +
            "join Supplier s on p.supplier.supplierID = s.supplierID " +
            "where p.detail.detailID = ?1")
    List<DetailInformationDto> findDetailInformation(@Param("detailID") Integer detailID);
}
