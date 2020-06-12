package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.entity.*;
import nsu.shserg.AutopartsDatabaseServer.exception.CellInStockNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.DetailNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.SupplierNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.DetailRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.PriceListRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin
public class SupplierController {
    private final SupplierRepository supplierRepository;
    private final PriceListRepository priceListRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository, PriceListRepository priceListRepository, DetailRepository detailRepository) {
        this.supplierRepository = supplierRepository;
        this.priceListRepository = priceListRepository;
        this.detailRepository = detailRepository;
    }

    @RequestMapping(method = GET, value = "supplier")
    public ResponseEntity<Supplier> getSupplier(@RequestParam Integer supplierID) {
        Optional<Supplier> optional =  supplierRepository.findById(supplierID);
        if(optional.isEmpty()){
            throw new SupplierNotFoundException();
        }
        return new ResponseEntity<Supplier>(optional.get(), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "supplierAll")
    public ResponseEntity<List<Supplier>> getAllSupplier() {
        return new ResponseEntity<List<Supplier>>(supplierRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "supplierAdd")
    public HttpStatus add(@RequestBody Supplier supplier){
        supplierRepository.save(supplier);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = PATCH, value = "supplierUpdate")
    public HttpStatus update(@RequestBody Supplier supplier){
        Optional<Supplier> optional = supplierRepository.findById(supplier.getSupplierID());
        if (optional.isEmpty()){
            throw new SupplierNotFoundException();
        }
        supplierRepository.save(supplier);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = POST, value = "supplierAddDetail")
    public HttpStatus supplierAddDetail(@RequestParam Integer supplierID, @RequestParam Integer detailID, @RequestParam int price){
        Optional<Detail> optionalDetail = detailRepository.findById(detailID);
        if(optionalDetail.isEmpty()){
            throw new DetailNotFoundException();
        }
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierID);
        if(optionalSupplier.isEmpty()){
            throw new SupplierNotFoundException();
        }
        PriceList priceList = new PriceList();
        priceList.setSupplier(optionalSupplier.get());
        priceList.setDetail(optionalDetail.get());
        priceList.setPrice(price);
        priceListRepository.save(priceList);

        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = DELETE, value = "supplierDropDetail")
    public HttpStatus supplierDropDetail(@RequestParam Integer supplierID, @RequestParam Integer detailID) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierID);
        if (optionalSupplier.isEmpty()) {
            throw new SupplierNotFoundException();
        }
        Optional<Detail> optionalDetail = detailRepository.findById(detailID);
        if (optionalDetail.isEmpty()) {
            throw new DetailNotFoundException();
        }
        Detail detail = optionalDetail.get();
        Supplier supplier = optionalSupplier.get();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        List<PriceList> priceListList = priceListRepository.findAllBySupplier(supplier);
        for (PriceList priceList : priceListList) {
            if (priceList.getDetail().getDetailID().equals(detail.getDetailID())) {
                priceListRepository.delete(priceList);
                httpStatus = HttpStatus.ACCEPTED;
            }
        }
        return httpStatus;
    }

    @RequestMapping(method = DELETE, value = "supplierDrop")
    public HttpStatus drop(@RequestParam Integer supplierID){
        Optional<Supplier> optional =  supplierRepository.findById(supplierID);
        if(optional.isEmpty()) {
            throw new SupplierNotFoundException();
        }
        Supplier supplier = optional.get();
        supplierRepository.delete(supplier);
        return HttpStatus.ACCEPTED;
    }
}