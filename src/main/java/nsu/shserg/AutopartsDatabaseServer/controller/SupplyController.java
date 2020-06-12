package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.dto.DetailDto;
import nsu.shserg.AutopartsDatabaseServer.dto.SupplyDto;
import nsu.shserg.AutopartsDatabaseServer.entity.*;
import nsu.shserg.AutopartsDatabaseServer.exception.DetailNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.SupplierNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.SupplyNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.DetailRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.SupplierRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.SupplyDetailRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@CrossOrigin
public class SupplyController {
    private final SupplyRepository supplyRepository;
    private final SupplyDetailRepository supplyDetailRepository;
    private final DetailRepository detailRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplyController(SupplyRepository supplyRepository, SupplyDetailRepository supplyDetailRepository, DetailRepository detailRepository, SupplierRepository supplierRepository) {
        this.supplyRepository = supplyRepository;
        this.supplyDetailRepository = supplyDetailRepository;
        this.detailRepository = detailRepository;
        this.supplierRepository = supplierRepository;
    }

    @RequestMapping(method = GET, value = "supply")
    public ResponseEntity<SupplyDto> getPurchase(@RequestParam Integer supplyID) {
        Optional<Supply> optional = supplyRepository.findById(supplyID);
        if (optional.isEmpty()) {
            throw new SupplyNotFoundException();
        }
        Supply supply = optional.get();
        return new ResponseEntity<SupplyDto>(translateToDto(supply), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "supplyAll")
    public ResponseEntity<List<SupplyDto>> getAllSupply() {
        List<Supply> supplyList = supplyRepository.findAll();
        List<SupplyDto> supplyDtoList = new ArrayList<>();
        for (Supply supply : supplyList) {
            supplyDtoList.add(translateToDto(supply));
        }
        return new ResponseEntity<List<SupplyDto>>(supplyDtoList, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "supplyAdd")
    public HttpStatus add(@RequestBody SupplyDto supplyDto) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplyDto.getSupplier().getSupplierID());
        if (supplierOptional.isEmpty()) {
            throw new SupplierNotFoundException();
        }
        List<DetailDto> listDetail = supplyDto.getDetailList();
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            if (detailOptional.isEmpty()) {
                throw new DetailNotFoundException();
            }
        }
        Supply supply = new Supply();
        supply.setSupplyID(supplyDto.getSupplyID());
        supply.setDeliveryDate(supplyDto.getDeliveryDate());
        supply.setSupplier(supplierOptional.get());
        supply.setMarriageRate(supplyDto.getMarriageRate());
        supply.setCustomsClearance(supplyDto.getCustomsClearance());
        supplyRepository.save(supply);
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            SupplyDetail supplyDetail = new SupplyDetail();
            supplyDetail.setDetail(detailOptional.get());
            supplyDetail.setSupply(supply);
            supplyDetail.setQuantity(detailDto.getQuantity());
            supplyDetailRepository.save(supplyDetail);
        }

        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = PATCH, value = "supplyUpdate")
    public HttpStatus update(@RequestBody SupplyDto supplyDto) {
        Optional<Supply> supplyOptional = supplyRepository.findById(supplyDto.getSupplyID());
        if (supplyOptional.isEmpty()) {
            throw new SupplyNotFoundException();
        }
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplyDto.getSupplier().getSupplierID());
        if (supplierOptional.isEmpty()) {
            throw new SupplierNotFoundException();
        }
        List<DetailDto> listDetail = supplyDto.getDetailList();
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            if (detailOptional.isEmpty()) {
                throw new DetailNotFoundException();
            }
        }
        Supply supply = supplyOptional.get();
        supply.setDeliveryDate(supplyDto.getDeliveryDate());
        supply.setSupplier(supplierOptional.get());
        supply.setMarriageRate(supplyDto.getMarriageRate());
        supply.setCustomsClearance(supplyDto.getCustomsClearance());
        supplyRepository.save(supply);
        List<SupplyDetail> supplyDetailList = supplyDetailRepository.findAllBySupply(supply);
        for (SupplyDetail supplyDetail : supplyDetailList) {
            supplyDetailRepository.delete(supplyDetail);
        }
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            SupplyDetail supplyDetail = new SupplyDetail();
            supplyDetail.setDetail(detailOptional.get());
            supplyDetail.setSupply(supply);
            supplyDetail.setQuantity(detailDto.getQuantity());
            supplyDetailRepository.save(supplyDetail);
        }
        return HttpStatus.ACCEPTED;
    }

//    @RequestMapping(method = POST, value = "purchaseAddDetail")
//    public HttpStatus purchaseAddDetail(@RequestParam Integer purchaseID, @RequestBody DetailDto detailDto){
//        Optional<Detail> optionalDetail = detailRepository.findById(detailDto.getDetailID());
//        if(optionalDetail.isEmpty()){
//            throw new DetailNotFoundException();
//        }
//        Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseID);
//        if(optionalDetail.isEmpty()){
//            throw new PurchaseNotFoundException();
//        }
//        PurchaseDetail purchaseDetail = new PurchaseDetail();
//        purchaseDetail.setDetail(optionalDetail.get());
//        purchaseDetail.setPurchase(optionalPurchase.get());
//        purchaseDetail.setQuantity(detailDto.getQuantity());
//        purchaseDetailRepository.save(purchaseDetail);
//        return HttpStatus.ACCEPTED;
//    }
//
//    @RequestMapping(method = DELETE, value = "purchaseDropDetail")
//    public HttpStatus purchaseDropDetail(@RequestParam Integer purchaseID, @RequestParam Integer detailID){
//        Optional<Detail> optionalDetail = detailRepository.findById(detailID);
//        if(optionalDetail.isEmpty()){
//            throw new DetailNotFoundException();
//        }
//        Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseID);
//        if(optionalDetail.isEmpty()){
//            throw new PurchaseNotFoundException();
//        }
//        Detail detail =optionalDetail.get();
//        Purchase purchase = optionalPurchase.get();
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        List<PurchaseDetail> purchaseDetailList = purchaseDetailRepository.findAllByPurchase(purchase);
//        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
//            if (purchaseDetail.getDetail().getDetailID().equals(detail.getDetailID())) {
//                purchaseDetailRepository.delete(purchaseDetail);
//                httpStatus = HttpStatus.ACCEPTED;
//            }
//        }
//        return httpStatus;
//    }

    @RequestMapping(method = DELETE, value = "supplyDrop")
    public HttpStatus drop(@RequestParam Integer supplyID) {
        Optional<Supply> optional = supplyRepository.findById(supplyID);
        if (optional.isEmpty()) {
            throw new SupplyNotFoundException();
        }
        Supply supply = optional.get();
        List<SupplyDetail> supplyDetailList = supplyDetailRepository.findAllBySupply(supply);
        for (SupplyDetail supplyDetail : supplyDetailList) {
            supplyDetailRepository.delete(supplyDetail);
        }
        supplyRepository.delete(supply);
        return HttpStatus.ACCEPTED;
    }

    private SupplyDto translateToDto(Supply supply){
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setSupplyID(supply.getSupplyID());
        supplyDto.setDeliveryDate(supply.getDeliveryDate());
        supplyDto.setSupplier(supply.getSupplier());
        supplyDto.setMarriageRate(supply.getMarriageRate());
        supplyDto.setCustomsClearance(supply.getCustomsClearance());
        List<SupplyDetail> supplyDetailList = supplyDetailRepository.findAllBySupply(supply);
        List<DetailDto> detailDtoList = new ArrayList<>();
        for (SupplyDetail supplyDetail : supplyDetailList) {
            Detail detail = supplyDetail.getDetail();
            DetailDto detailDto = new DetailDto();
            detailDto.setDetailID(detail.getDetailID());
            detailDto.setName(detail.getName());
            detailDto.setPrice(detail.getPrice());
            detailDto.setQuantity(supplyDetail.getQuantity());
            detailDtoList.add(detailDto);
        }
        supplyDto.setDetailList(detailDtoList);
        return supplyDto;
    }
}