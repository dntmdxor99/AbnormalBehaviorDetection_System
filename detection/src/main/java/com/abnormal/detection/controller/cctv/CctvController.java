package com.abnormal.detection.controller.cctv;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.service.cctv.CctvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cctvs")
public class CctvController {

    private final CctvService cctvService;

    @Autowired
    public CctvController(CctvService cctvService) {
        this.cctvService = cctvService;
    }

    @PostMapping("/create")
    public Cctv createCctv(@RequestBody Cctv cctv) {
        return cctvService.createCctv(cctv);
    }

    @GetMapping("/allCctv")
    public List<Cctv> getAllCctvs() {
        return cctvService.getAllCctvs();
    }

    @DeleteMapping("/delete/{cctvId}")
    public ResponseEntity<String> deleteCctv(@PathVariable Long cctvId) {
        try {
            cctvService.deleteCctv(cctvId);
            return new ResponseEntity<>("Cctv with ID " + cctvId + " has been deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting Cctv with ID " + cctvId + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cctvId}")
    public Cctv getCctvById(@PathVariable Long cctvId) {
        return cctvService.getCctvById(cctvId);
    }



    //위도경도
    /*
    @GetMapping("/location")
    public ResponseEntity<List<Cctv>> getCctvsByLocation(
            @RequestParam("latitude") Float latitude,
            @RequestParam("longitude") Float longitude) {
        List<Cctv> cctvs = cctvService.getCctvsByLocation(latitude, longitude);
        return ResponseEntity.ok(cctvs);
    }

    @GetMapping("/location/near")
    public ResponseEntity<List<Cctv>> getCctvsNearLocation(
            @RequestParam("latitude") Float latitude,
            @RequestParam("longitude") Float longitude,
            @RequestParam("distance") double distance) {
        List<Cctv> cctvs = cctvService.getCctvsNearLocation(latitude, longitude, distance);
        return ResponseEntity.ok(cctvs);
    }

    @GetMapping("/location/distance")
    public ResponseEntity<List<Cctv>> getCctvsByLocationAndDistance(
            @RequestParam("latitude") Float latitude,
            @RequestParam("longitude") Float longitude,
            @RequestParam("distance") double distance) {
        List<Cctv> cctvs = cctvService.getCctvsByLocationAndDistance(latitude, longitude, distance);
        return ResponseEntity.ok(cctvs);
    }

     */

    //

    @PutMapping("/{cctvId}")
    public Cctv updateCctv(@PathVariable Long cctvId, @RequestBody Cctv updatedCctv) {
        return cctvService.updateCctv(cctvId, updatedCctv);
    }
/*
    @DeleteMapping("/{cctvId}")
    public void deleteCctv(@PathVariable Long cctvId) {
        cctvService.deleteCctv(cctvId);
    }

 */
/*
    @PostMapping("/search")
    public List<Cctv> searchCctvs(
            @RequestParam(required = false) String cctvName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean is360Degree,
            @RequestParam(required = false) String protocol
    ) {
        return cctvService.searchCctvsByOptions(cctvName, location, is360Degree, protocol);
    }

 */

    @PostMapping("/name")
    public List<Cctv> searchCctvsByName(@RequestBody Map<String, String> requestBody) {
        String cctvName = requestBody.get("cctvName");
        return cctvService.getCctvByName(cctvName);
    }
    @PostMapping("/search")
    public List<Cctv> searchCctvs(@RequestBody List<Map<String, String>> searchRequests) {
        List<Cctv> result = new ArrayList<>();

        for (Map<String, String> searchRequest : searchRequests) {
            String cctvIdStr = searchRequest.get("cctvId");
            Long cctvId = (cctvIdStr != null && !cctvIdStr.isEmpty()) ? Long.valueOf(cctvIdStr) : null;
            String cctvName = searchRequest.get("cctvName");
            String location = searchRequest.get("location");
            Boolean is360Degree = Boolean.valueOf(searchRequest.get("is360Degree"));
            String channel = searchRequest.get("channel");

            List<Cctv> cctvList = cctvService.searchCctvsByOptions(cctvId, cctvName, location, is360Degree, channel);
            result.addAll(cctvList);
        }

        return result;
    }


    @GetMapping("/filter")
    public List<Cctv> filterCctvs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean is360Degree,
            @RequestParam(required = false) String channel
    ) {
        return cctvService.filterCctvsByCriteria(location, is360Degree, channel);
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
