//package nsu.shserg.AutopartsDatabaseServer.service;
//
//import nsu.shserg.AutopartsDatabaseServer.dto.DetailDto;
//import nsu.shserg.AutopartsDatabaseServer.dto.PurchaseDto;
//import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
//import nsu.shserg.AutopartsDatabaseServer.entity.Purchase;
//import nsu.shserg.AutopartsDatabaseServer.entity.PurchaseDetail;
//import nsu.shserg.AutopartsDatabaseServer.exception.PurchaseNotFoundException;
//import nsu.shserg.AutopartsDatabaseServer.repository.BuyerRepository;
//import nsu.shserg.AutopartsDatabaseServer.repository.DetailRepository;
//import nsu.shserg.AutopartsDatabaseServer.repository.PurchaseDetailRepository;
//import nsu.shserg.AutopartsDatabaseServer.repository.PurchaseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PurchaseService {
//    private final PurchaseRepository purchaseRepository;
//    private final BuyerRepository buyerRepository;
//    private final DetailRepository detailRepository;
//    private final PurchaseDetailRepository purchaseDetailRepository;
//
//    @Autowired
//    public PurchaseService(PurchaseRepository purchaseRepository, BuyerRepository buyerRepository,
//                              DetailRepository detailRepository, PurchaseDetailRepository purchaseDetailRepository) {
//        this.purchaseRepository = purchaseRepository;
//        this.buyerRepository = buyerRepository;
//        this.detailRepository = detailRepository;
//        this.purchaseDetailRepository = purchaseDetailRepository;
//    }
//
//    public PurchaseDto getPurchase(Integer purchaseID) {
//        Optional<Purchase> optional = purchaseRepository.findById(purchaseID);
//        if (optional.isEmpty()) {
//            throw new PurchaseNotFoundException();
//        }
//        Purchase purchase = optional.get();
//        return translateToDto(purchase);
//
//    }
//
//
//
//    public List<PurchaseDto> getAllPurchase() {
//
//        return purchaseDtoList;
//    }
//}
