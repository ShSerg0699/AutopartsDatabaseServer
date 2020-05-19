package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.entity.Supply;
import nsu.shserg.AutopartsDatabaseServer.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
public class SupplyController {
    private final SupplyRepository supplyRepository;

    @Autowired
    public SupplyController(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @RequestMapping(method = GET, value = "supply")
    public Supply getSupply(@RequestParam(required = true)  Integer supplyID) {
        Optional<Supply> optional =  supplyRepository.findById(supplyID);
        Supply supply = optional.get();
        return supply;
    }

    @RequestMapping(method = GET, value = "supplyAll")
    public List<Supply> getAllSupply() {
        return supplyRepository.findAll();
    }

    @RequestMapping(method = POST, value = "supplyAdd")
    public Supply add(@RequestBody Supply supply){
        return supplyRepository.save(supply);
    }

    @RequestMapping(method = PATCH, value = "supplyUpdate")
    public Supply update(@RequestBody Supply supply){
        Optional<Supply> optional = supplyRepository.findById(supply.getSupplyID());
        if (optional.isEmpty()){
            return null;
        }
        return supplyRepository.save(supply);
    }

    @RequestMapping(method = DELETE, value = "supplyDrop")
    public void drop(@RequestParam(required = true)  Integer supplyID){
        Optional<Supply> optional =  supplyRepository.findById(supplyID);
        if(!optional.isEmpty()) {
            Supply supply = optional.get();
            supplyRepository.delete(supply);
        }
    }
}