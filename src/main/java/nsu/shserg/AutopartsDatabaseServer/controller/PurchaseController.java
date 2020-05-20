package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.dto.DetailDto;
import nsu.shserg.AutopartsDatabaseServer.dto.PurchaseDto;
import nsu.shserg.AutopartsDatabaseServer.entity.*;
import nsu.shserg.AutopartsDatabaseServer.exception.BuyerNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.DetailNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.PurchaseNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.BuyerRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.DetailRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.PurchaseDetailRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class PurchaseController {
    private final PurchaseRepository purchaseRepository;
    private final BuyerRepository buyerRepository;
    private final DetailRepository detailRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository, BuyerRepository buyerRepository,
                              DetailRepository detailRepository, PurchaseDetailRepository purchaseDetailRepository) {
        this.purchaseRepository = purchaseRepository;
        this.buyerRepository = buyerRepository;
        this.detailRepository = detailRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
    }

    @RequestMapping(method = GET, value = "purchase")
    public ResponseEntity<PurchaseDto> getPurchase(@RequestParam Integer purchaseID) {
        Optional<Purchase> optional = purchaseRepository.findById(purchaseID);
        if (optional.isEmpty()) {
            throw new PurchaseNotFoundException();
        }
        Purchase purchase = optional.get();
        return new ResponseEntity<PurchaseDto>(translateToDto(purchase), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "purchaseAll")
    public ResponseEntity<List<PurchaseDto>> getAllPurchase() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        for (Purchase purchase : purchaseList) {
            purchaseDtoList.add(translateToDto(purchase));
        }
        return new ResponseEntity<List<PurchaseDto>>(purchaseDtoList, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "purchaseAdd")
    public HttpStatus add(@RequestBody PurchaseDto purchaseDto) {
        Optional<Buyer> buyerOptional = buyerRepository.findById(purchaseDto.getBuyer().getBuyerID());
        if (buyerOptional.isEmpty()) {
            throw new BuyerNotFoundException();
        }
        List<DetailDto> listDetail = purchaseDto.getDetailList();
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            if (detailOptional.isEmpty()) {
                throw new DetailNotFoundException();
            }
        }
        Purchase purchase = new Purchase();
        purchase.setPurchaseID(purchaseDto.getPurchaseID());
        purchase.setPurchaseDate(purchaseDto.getPurchaseDate());
        purchase.setBuyer(buyerOptional.get());
        purchaseRepository.save(purchase);
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setDetail(detailOptional.get());
            purchaseDetail.setPurchase(purchase);
            purchaseDetail.setQuantity(detailDto.getQuantity());
            purchaseDetailRepository.save(purchaseDetail);
        }

        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = PATCH, value = "purchaseUpdate")
    public HttpStatus update(@RequestBody PurchaseDto purchaseDto) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseDto.getPurchaseID());
        if (optionalPurchase.isEmpty()) {
            throw new PurchaseNotFoundException();
        }
        Optional<Buyer> buyerOptional = buyerRepository.findById(purchaseDto.getBuyer().getBuyerID());
        if (buyerOptional.isEmpty()) {
            throw new BuyerNotFoundException();
        }
        List<DetailDto> listDetail = purchaseDto.getDetailList();
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> detailOptional = detailRepository.findById(detailDto.getDetailID());
            if (detailOptional.isEmpty()) {
                throw new DetailNotFoundException();
            }
        }
        Purchase purchase = optionalPurchase.get();
        purchase.setPurchaseDate(purchaseDto.getPurchaseDate());
        purchase.setBuyer(buyerOptional.get());
        purchaseRepository.save(purchase);
        List<PurchaseDetail> purchaseDetailList = purchaseDetailRepository.findAllByPurchase(purchase);
        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
            purchaseDetailRepository.delete(purchaseDetail);
        }
        for (DetailDto detailDto : listDetail) {
            Optional<Detail> optionalDetail = detailRepository.findById(detailDto.getDetailID());
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setDetail(optionalDetail.get());
            purchaseDetail.setPurchase(purchase);
            purchaseDetail.setQuantity(detailDto.getQuantity());
            purchaseDetailRepository.save(purchaseDetail);
        }

        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = POST, value = "purchaseAddDetail")
    public HttpStatus purchaseAddDetail(@RequestParam Integer purchaseID, @RequestBody DetailDto detailDto){
        Optional<Detail> optionalDetail = detailRepository.findById(detailDto.getDetailID());
        if(optionalDetail.isEmpty()){
            throw new DetailNotFoundException();
        }
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseID);
        if(optionalDetail.isEmpty()){
            throw new PurchaseNotFoundException();
        }
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setDetail(optionalDetail.get());
        purchaseDetail.setPurchase(optionalPurchase.get());
        purchaseDetail.setQuantity(detailDto.getQuantity());
        purchaseDetailRepository.save(purchaseDetail);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = DELETE, value = "purchaseDropDetail")
    public HttpStatus purchaseDropDetail(@RequestParam Integer purchaseID, @RequestParam Integer detailID){
        Optional<Detail> optionalDetail = detailRepository.findById(detailID);
        if(optionalDetail.isEmpty()){
            throw new DetailNotFoundException();
        }
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseID);
        if(optionalDetail.isEmpty()){
            throw new PurchaseNotFoundException();
        }
        Detail detail =optionalDetail.get();
        Purchase purchase = optionalPurchase.get();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        List<PurchaseDetail> purchaseDetailList = purchaseDetailRepository.findAllByPurchase(purchase);
        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
            if (purchaseDetail.getDetail().getDetailID().equals(detail.getDetailID())) {
                purchaseDetailRepository.delete(purchaseDetail);
                httpStatus = HttpStatus.ACCEPTED;
            }
        }
        return httpStatus;
    }

    @RequestMapping(method = DELETE, value = "purchaseDrop")
    public HttpStatus drop(@RequestParam Integer purchaseID) {
        Optional<Purchase> optional = purchaseRepository.findById(purchaseID);
        if (!optional.isEmpty()) {
            throw new PurchaseNotFoundException();
        }
        Purchase purchase = optional.get();
        List<PurchaseDetail> purchaseDetailList = purchaseDetailRepository.findAllByPurchase(purchase);
        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
            purchaseDetailRepository.delete(purchaseDetail);
        }
        purchaseRepository.delete(purchase);
        return HttpStatus.ACCEPTED;
    }

    private PurchaseDto translateToDto(Purchase purchase){
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setPurchaseID(purchase.getPurchaseID());
        purchaseDto.setPurchaseDate(purchase.getPurchaseDate());
        purchaseDto.setBuyer(purchase.getBuyer());
        List<PurchaseDetail> purchaseDetailList = purchaseDetailRepository.findAllByPurchase(purchase);
        List<DetailDto> detailDtoList = new ArrayList<>();
        for (PurchaseDetail purchaseDetail : purchaseDetailList) {
            Detail detail = purchaseDetail.getDetail();
            DetailDto detailDto = new DetailDto();
            detailDto.setDetailID(detail.getDetailID());
            detailDto.setName(detail.getName());
            detailDto.setPrice(detail.getPrice());
            detailDto.setQuantity(purchaseDetail.getQuantity());
            detailDtoList.add(detailDto);
        }
        purchaseDto.setDetailList(detailDtoList);
        return purchaseDto;
    }
}
