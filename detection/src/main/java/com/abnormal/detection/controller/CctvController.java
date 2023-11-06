package com.abnormal.detection.controller;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.service.CctvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cctvs")
public class CctvController {

    private final CctvService cctvService;

    @Autowired
    public CctvController(CctvService cctvService) {
        this.cctvService = cctvService;
    }

    @PostMapping
    public Cctv createCctv(@RequestBody Cctv cctv) {
        return cctvService.createCctv(cctv);
    }

    @GetMapping("/{cctvId}")
    public Cctv getCctvById(@PathVariable Long cctvId) {
        return cctvService.getCctvById(cctvId);
    }

    @GetMapping
    public List<Cctv> getAllCctvs() {
        return cctvService.getAllCctvs();
    }

    @PutMapping("/{cctvId}")
    public Cctv updateCctv(@PathVariable Long cctvId, @RequestBody Cctv updatedCctv) {
        return cctvService.updateCctv(cctvId, updatedCctv);
    }

    @DeleteMapping("/{cctvId}")
    public void deleteCctv(@PathVariable Long cctvId) {
        cctvService.deleteCctv(cctvId);
    }

    @GetMapping("/search")
    public List<Cctv> searchCctvs(
            @RequestParam(required = false) String cctvName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean is360Degree,
            @RequestParam(required = false) String protocol
    ) {
        return cctvService.searchCctvsByOptions(cctvName, location, is360Degree, protocol);
    }

    @GetMapping("/filter")
    public List<Cctv> filterCctvs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean is360Degree,
            @RequestParam(required = false) String protocol
    ) {
        return cctvService.filterCctvsByCriteria(location, is360Degree, protocol);
    }

    @GetMapping("/pagination")
    public List<Cctv> getCctvsWithPagination(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return cctvService.getCctvsWithPagination(page, pageSize, sortBy);
    }

    @GetMapping("/count")
    public int countAllCctvs() {
        return cctvService.countAllCctvs();
    }
}
