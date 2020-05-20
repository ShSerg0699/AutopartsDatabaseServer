package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import nsu.shserg.AutopartsDatabaseServer.entity.PurchaseDetail;
import nsu.shserg.AutopartsDatabaseServer.exception.DetailNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class DetailController {
    private final DetailRepository detailRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final SupplyDetailRepository supplyDetailRepository;
    private final CellDetailRepository cellDetailRepository;
    private final PriceListRepository priceListRepository;

    @Autowired
    public DetailController(DetailRepository detailRepository, PurchaseDetailRepository purchaseDetailRepository,
                            SupplyDetailRepository supplyDetailRepository, CellDetailRepository cellDetailRepository,
                            PriceListRepository priceListRepository) {
        this.detailRepository = detailRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.supplyDetailRepository = supplyDetailRepository;
        this.cellDetailRepository = cellDetailRepository;
        this.priceListRepository = priceListRepository;
    }

    @RequestMapping(method = GET, value = "detail")
    public ResponseEntity<Detail> getDetail(@RequestParam Integer detailID) {
        Optional<Detail> optional = detailRepository.findById(detailID);
        if (optional.isEmpty()) {
            throw new DetailNotFoundException();
        }
        return new ResponseEntity<Detail>(optional.get(), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "detailAll")
    public List<Detail> getAllDetail() {
        return detailRepository.findAll();
    }

    @RequestMapping(method = POST, value = "detailAdd")
    public HttpStatus add(@RequestBody Detail detail) {
        detailRepository.save(detail);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = PATCH, value = "detailUpdate")
    public HttpStatus update(@RequestBody Detail detail) {
        Optional<Detail> optional = detailRepository.findById(detail.getDetailID());
        if (optional.isEmpty()) {
            throw new DetailNotFoundException();
        }
        detailRepository.save(detail);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = DELETE, value = "detailDrop")
    public HttpStatus drop(@RequestParam Integer detailID) {
        Optional<Detail> optional = detailRepository.findById(detailID);
        if (!optional.isEmpty()) {
            throw new DetailNotFoundException();
        }
        Detail detail = optional.get();
        List<PurchaseDetail> purchaseDetailList = purchaseDetailRepository.findAllByDetail(detail);
        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
            purchaseDetailRepository.delete(purchaseDetail);
        }
//fixme: drop cascade
        detailRepository.delete(detail);
        return HttpStatus.ACCEPTED;
    }
}