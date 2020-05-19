package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.entity.Supplier;
import nsu.shserg.AutopartsDatabaseServer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class SupplierController {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @RequestMapping(method = GET, value = "supplier")
    public Supplier getSupplier(@RequestParam(required = true)  Integer supplierID) {
        Optional<Supplier> optional =  supplierRepository.findById(supplierID);
        Supplier supplier = optional.get();
        return supplier;
    }

    @RequestMapping(method = GET, value = "supplierAll")
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    @RequestMapping(method = POST, value = "supplierAdd")
    public Supplier add(@RequestBody Supplier supplier){
        return supplierRepository.save(supplier);
    }

    @RequestMapping(method = PATCH, value = "supplierUpdate")
    public Supplier update(@RequestBody Supplier supplier){
        Optional<Supplier> optional = supplierRepository.findById(supplier.getSupplierID());
        if (optional.isEmpty()){
            return null;
        }
        return supplierRepository.save(supplier);
    }

    @RequestMapping(method = DELETE, value = "supplierDrop")
    public void drop(@RequestParam(required = true)  Integer supplierID){
        Optional<Supplier> optional =  supplierRepository.findById(supplierID);
        if(!optional.isEmpty()) {
            Supplier supplier = optional.get();
            supplierRepository.delete(supplier);
        }
    }
}