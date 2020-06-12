package nsu.shserg.AutopartsDatabaseServer.controller;

import nsu.shserg.AutopartsDatabaseServer.dto.CellInStockDto;
import nsu.shserg.AutopartsDatabaseServer.dto.DetailInCellDto;
import nsu.shserg.AutopartsDatabaseServer.entity.CellDetail;
import nsu.shserg.AutopartsDatabaseServer.entity.CellInStock;
import nsu.shserg.AutopartsDatabaseServer.entity.Detail;
import nsu.shserg.AutopartsDatabaseServer.exception.CellInStockNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.exception.CellNotEnoughStorageException;
import nsu.shserg.AutopartsDatabaseServer.exception.DetailNotFoundException;
import nsu.shserg.AutopartsDatabaseServer.repository.CellDetailRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.CellInStockRepository;
import nsu.shserg.AutopartsDatabaseServer.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin
public class CellInStockController {
    private final CellInStockRepository cellInStockRepository;
    private final DetailRepository detailRepository;
    private final CellDetailRepository cellDetailRepository;

    @Autowired
    public CellInStockController(CellInStockRepository cellInStockRepository, DetailRepository detailRepository, CellDetailRepository cellDetailRepository) {
        this.cellInStockRepository = cellInStockRepository;
        this.detailRepository = detailRepository;
        this.cellDetailRepository = cellDetailRepository;
    }

    @RequestMapping(method = GET, value = "cell")
    public ResponseEntity<CellInStockDto> getCell(@RequestParam Integer cellID) {
        Optional<CellInStock> optional = cellInStockRepository.findById(cellID);
        if (optional.isEmpty()) {
            throw new CellInStockNotFoundException();
        }
        CellInStock cell = optional.get();
        CellInStockDto cellInStockDto = new CellInStockDto();
        cellInStockDto.setCellID(cell.getCellID());
        cellInStockDto.setSpace(cell.getSpace());
        List<CellDetail> cellDetailList = cellDetailRepository.findAllByCellInStock(cell);
        List<DetailInCellDto> detailDtoList = new ArrayList<>();
        for (CellDetail cellDetail : cellDetailList) {
            Detail detail = cellDetail.getDetail();
            DetailInCellDto detailDto = new DetailInCellDto();
            detailDto.setDetailID(detail.getDetailID());
            detailDto.setSize(detail.getSize());
            detailDto.setQuantity(cellDetail.getQuantity());
            detailDto.setAppearanceDate(cellDetail.getAppearanceDate());
            detailDtoList.add(detailDto);
        }
        cellInStockDto.setDetailList(detailDtoList);
        return new ResponseEntity<CellInStockDto>(cellInStockDto, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "cellAll")
    public ResponseEntity<List<CellInStockDto>> getAllCell() {
        List<CellInStock> cellList = cellInStockRepository.findAll();
        List<CellInStockDto> cellDtoList = new ArrayList<>();
        for (CellInStock cell : cellList) {
            CellInStockDto cellInStockDto = new CellInStockDto();
            cellInStockDto.setCellID(cell.getCellID());
            cellInStockDto.setSpace(cell.getSpace());
            List<CellDetail> cellDetailList = cellDetailRepository.findAllByCellInStock(cell);
            List<DetailInCellDto> detailDtoList = new ArrayList<>();
            for (CellDetail cellDetail : cellDetailList) {
                Detail detail = cellDetail.getDetail();
                DetailInCellDto detailDto = new DetailInCellDto();
                detailDto.setDetailID(detail.getDetailID());
                detailDto.setSize(detail.getSize());
                detailDto.setQuantity(cellDetail.getQuantity());
                detailDto.setAppearanceDate(cellDetail.getAppearanceDate());
                detailDtoList.add(detailDto);
            }
            cellInStockDto.setDetailList(detailDtoList);
            cellDtoList.add(cellInStockDto);
        }
        return new ResponseEntity<List<CellInStockDto>>(cellDtoList, HttpStatus.OK);
    }

    @RequestMapping(method = POST, value = "cellAdd")
    public HttpStatus add(@RequestBody CellInStock cell) {
        cellInStockRepository.save(cell);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = PATCH, value = "cellUpdate")
    public HttpStatus update(@RequestBody CellInStock cell) {
        Optional<CellInStock> optional = cellInStockRepository.findById(cell.getCellID());
        if (optional.isEmpty()) {
            throw new CellInStockNotFoundException();
        }
        cellInStockRepository.save(cell);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = POST, value = "cellAddDetail")
    public HttpStatus cellAddDetail(@RequestParam Integer cellID, @RequestBody DetailInCellDto detailInCellDto) {
        Optional<CellInStock> cellOptional = cellInStockRepository.findById(cellID);
        if (cellOptional.isEmpty()) {
            throw new CellInStockNotFoundException();
        }
        Optional<Detail> detailOptional = detailRepository.findById(detailInCellDto.getDetailID());
        if (detailOptional.isEmpty()) {
            throw new DetailNotFoundException();
        }
        Detail detail = detailOptional.get();
        CellInStock cell = cellOptional.get();
        List<CellDetail> cellDetailList = cellDetailRepository.findAllByCellInStock(cell);
        int occupiedSpace = 0;
        for (CellDetail cellDetail : cellDetailList) {
            occupiedSpace += cellDetail.getDetail().getSize() * cellDetail.getQuantity();
        }
        if (detail.getSize() * detailInCellDto.getQuantity() > cell.getSpace() - occupiedSpace) {
            throw new CellNotEnoughStorageException();
        }
        CellDetail cellDetail = new CellDetail();
        cellDetail.setCellInStock(cell);
        cellDetail.setDetail(detail);
        cellDetail.setQuantity(detailInCellDto.getQuantity());
        cellDetail.setAppearanceDate(detailInCellDto.getAppearanceDate());
        cellDetailRepository.save(cellDetail);

        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(method = DELETE, value = "cellDropDetail")
    public HttpStatus cellDropDetail(@RequestParam Integer cellID, @RequestParam Integer detailID) {
        Optional<CellInStock> cellOptional = cellInStockRepository.findById(cellID);
        if (cellOptional.isEmpty()) {
            throw new CellInStockNotFoundException();
        }
        Optional<Detail> detailOptional = detailRepository.findById(detailID);
        if (detailOptional.isEmpty()) {
            throw new DetailNotFoundException();
        }
        Detail detail = detailOptional.get();
        CellInStock cell = cellOptional.get();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        List<CellDetail> cellDetailList = cellDetailRepository.findAllByCellInStock(cell);
        for (CellDetail cellDetail : cellDetailList) {
            if (cellDetail.getDetail().getDetailID().equals(detail.getDetailID())) {
                cellDetailRepository.delete(cellDetail);
                httpStatus = HttpStatus.ACCEPTED;
            }
        }
        return httpStatus;
    }

    @RequestMapping(method = DELETE, value = "cellDrop")
    public HttpStatus drop(@RequestParam Integer cellID) {
        Optional<CellInStock> optional = cellInStockRepository.findById(cellID);
        if (optional.isEmpty()) {
            throw new CellInStockNotFoundException();
        }
        CellInStock cell = optional.get();
        cellInStockRepository.delete(cell);
        return HttpStatus.ACCEPTED;
    }
}
