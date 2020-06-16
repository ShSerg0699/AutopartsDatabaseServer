package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.dto.*;
import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import nsu.shserg.AutopartsDatabaseServer.entity.CellInStock;
import nsu.shserg.AutopartsDatabaseServer.entity.Supplier;
import nsu.shserg.AutopartsDatabaseServer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@CrossOrigin
public class QueryController {
    private final SupplierRepository supplierRepository;
    private final PriceListRepository priceListRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final SupplyDetailRepository supplyDetailRepository;
    private final BuyerRepository buyerRepository;
    private final CellDetailRepository cellDetailRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public QueryController(SupplierRepository supplierRepository, PriceListRepository priceListRepository,
                           PurchaseDetailRepository purchaseDetailRepository, SupplyDetailRepository supplyDetailRepository,
                           BuyerRepository buyerRepository, CellDetailRepository cellDetailRepository, DetailRepository detailRepository) {
        this.supplierRepository = supplierRepository;
        this.priceListRepository = priceListRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.supplyDetailRepository = supplyDetailRepository;
        this.buyerRepository = buyerRepository;
        this.cellDetailRepository = cellDetailRepository;
        this.detailRepository = detailRepository;
    }

    @RequestMapping(method = GET, value = "selectSupplierByParam")
    public ResponseEntity<List<Supplier>> selectSupplierByParam(@RequestParam Integer type, @RequestParam Integer detailID, @RequestParam Integer quantity,
                                                                @RequestParam Date deliveryDateStart, @RequestParam Date deliveryDateEnd) {
        return new ResponseEntity<>(supplierRepository.findSupplierByParam(type,detailID,quantity,deliveryDateStart,deliveryDateEnd), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectDetailInformation")
    public ResponseEntity<List<DetailInformationDto>> selectDetailInformation(@RequestParam Integer detailID) {
        return new ResponseEntity<>(priceListRepository.findDetailInformation(detailID), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectBuyerByParam")
    public ResponseEntity<List<Buyer>> selectBuyerByParam(@RequestParam Integer detailID, @RequestParam Integer quantity,
                                                          @RequestParam Date purchaseDateStart, @RequestParam Date purchaseDateEnd) {
        return new ResponseEntity<>(buyerRepository.findBuyerByParam(detailID, quantity, purchaseDateStart, purchaseDateEnd), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectDetailInStock")
    public ResponseEntity<List<SearchDetailInStockDto>> selectDetailInformation() {
        return new ResponseEntity<>(cellDetailRepository.findDetailInStock(), HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(method = GET, value = "selectTopDetailInPurchase")
    public ResponseEntity<List<TopDetailDto>> selectTopDetailInPurchase() {
        Page<TopDetailDto> page = purchaseDetailRepository.findTopDetailInPurchase(PageRequest.of(0, 10));
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = GET, value = "selectTopDetailInSupply")
    public ResponseEntity<List<TopDetailDto>> selectTopDetailInSupply() {
        Page<TopDetailDto> page = supplyDetailRepository.findTopDetailInSupply(PageRequest.of(0, 10));
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectSpecificSupplierGoodsShare")
    public ResponseEntity<Double> selectSpecificSupplierGoodsShare(@RequestParam Integer supplierID,
                                                                           @RequestParam Date deliveryDateStart,
                                                                           @RequestParam Date deliveryDateEnd) {
        return new ResponseEntity<>(supplierRepository.findSpecificSupplierGoodsShare(supplierID,deliveryDateStart,deliveryDateEnd), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectOverhead")
    public ResponseEntity<Double> selectOverhead() {
        return new ResponseEntity<>(detailRepository.findOverhead(), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectStockpiledDetail")
    public ResponseEntity<List<StockpiledDetailDto>> selectStockpiledDetail(@RequestParam Date purchaseDateStart, @RequestParam Date purchaseDateEnd) {
        return new ResponseEntity<>(cellDetailRepository.findStockpiledDetail(purchaseDateStart, purchaseDateEnd), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectSoldDetailByDate")
    public ResponseEntity<List<SoldDetailDto>> selectSoldDetailByDate(@RequestParam Date purchaseDate) {
        return new ResponseEntity<>(purchaseDetailRepository.findSoldDetailByDate(purchaseDate), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectCashReport")
    public ResponseEntity<List<CashReportDto>> selectCashReport(@RequestParam Date purchaseDateStart, @RequestParam Date purchaseDateEnd) {
        return new ResponseEntity<>(purchaseDetailRepository.findCashReport(purchaseDateStart, purchaseDateEnd), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectInventoryList")
    public ResponseEntity<List<InventoryListDto>> selectInventoryList(@RequestParam Date appearanceDateStart, @RequestParam Date appearanceDateEnd) {
        return new ResponseEntity<>(cellDetailRepository.findInventoryList(appearanceDateStart, appearanceDateEnd), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectDetailSpeedSale")
    public ResponseEntity<List<DetailSpeedSaleDto>> selectDetailSpeedSale() {
        return new ResponseEntity<>(detailRepository.findGoodsSpeedSale(), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "selectEmptyCell")
    public ResponseEntity<List<CellInStock>> selectEmptyCell() {
        return new ResponseEntity<>(cellDetailRepository.findEmptyCell(), HttpStatus.OK);
    }
}
