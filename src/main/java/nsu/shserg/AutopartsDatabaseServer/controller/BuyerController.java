package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import nsu.shserg.AutopartsDatabaseServer.exception.BuyerNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.BuyerRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin
public class BuyerController {
    private final BuyerRepository buyerRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public BuyerController(BuyerRepository buyerRepository, PurchaseRepository purchaseRepository) {
        this.buyerRepository = buyerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @RequestMapping(method = GET, value = "buyer")
    public ResponseEntity<Buyer> getDetail(@RequestParam Integer buyerID) {
        Optional<Buyer> optional = buyerRepository.findById(buyerID);
        if (optional.isEmpty()){
            throw new BuyerNotFoundException();
        }
        return new ResponseEntity<Buyer>(optional.get(), HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "buyerAll")
    public ResponseEntity<List<Buyer>> getAllDetail() {
        return new ResponseEntity<List<Buyer>>(buyerRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "buyerAdd")
    public HttpStatus add(@RequestBody Buyer buyer) {
        buyerRepository.save(buyer);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = PATCH, value = "buyerUpdate")
    public HttpStatus update(@RequestBody Buyer buyer) {
        Optional<Buyer> optional = buyerRepository.findById(buyer.getBuyerID());
        if (optional.isEmpty()) {
            throw new BuyerNotFoundException();
        }
        buyerRepository.save(buyer);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = DELETE, value = "buyerDrop")
    public HttpStatus drop(@RequestParam Integer buyerID) {
        Optional<Buyer> optional = buyerRepository.findById(buyerID);
        if (optional.isEmpty()) {
            throw new BuyerNotFoundException();
        }
        //fixme: drop cascade
        buyerRepository.delete(optional.get());
        return HttpStatus.ACCEPTED;
    }
}