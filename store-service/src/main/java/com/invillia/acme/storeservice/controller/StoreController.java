package com.invillia.acme.storeservice.controller;

import com.invillia.acme.storeservice.dto.StoreDTO;
import com.invillia.acme.storeservice.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody @Valid StoreDTO storeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addStore(storeDTO));
    }

    @GetMapping
    public List<StoreDTO> findStores(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String address) {
        return storeService.findAllByParameters(address, name);
    }

    @PutMapping("/{identifier}")
    public StoreDTO updateStore(@PathVariable String identifier, @RequestBody @Valid StoreDTO storeDTO) {
        return storeService.updateStore(identifier, storeDTO);
    }
}
