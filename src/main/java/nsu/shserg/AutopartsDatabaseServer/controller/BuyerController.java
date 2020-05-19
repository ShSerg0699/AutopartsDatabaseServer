package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import nsu.shserg.AutopartsDatabaseServer.exception.BuyerNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.BuyerRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class BuyerController {
    private final BuyerRepository buyerRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public BuyerController(BuyerRepository buyerRepository, PurchaseRepository purchaseRepository) {
        this.buyerRepository = buyerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @RequestMapping(method = GET, value = "buyer")
    public Buyer getDetail(@RequestParam(required = true) Integer buyerID) {
        Optional<Buyer> optional = buyerRepository.findById(buyerID);
        Buyer buyer = optional.get();
        return buyer;
    }

    @RequestMapping(method = GET, value = "buyerAll")
    public List<Buyer> getAllDetail() {
        return buyerRepository.findAll();
    }

    @RequestMapping(method = POST, value = "buyerAdd")
    public Buyer add(@RequestBody Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @RequestMapping(method = PATCH, value = "buyerUpdate")
    public Buyer update(@RequestBody Buyer buyer) {
        Optional<Buyer> optional = buyerRepository.findById(buyer.getBuyerID());
        if (optional.isEmpty()) {
            return null;
        }
        return buyerRepository.save(buyer);
    }

    @RequestMapping(method = DELETE, value = "buyerDrop")
    public HttpStatus drop(@RequestParam(required = true) Integer buyerID) {
        Optional<Buyer> optional = buyerRepository.findById(buyerID);
        if (!optional.isEmpty()) {
            throw new BuyerNotFoundException();
        }
        //fixme: drop cascade
        Buyer buyer = optional.get();
        buyerRepository.delete(buyer);
        return HttpStatus.ACCEPTED;
    }
}