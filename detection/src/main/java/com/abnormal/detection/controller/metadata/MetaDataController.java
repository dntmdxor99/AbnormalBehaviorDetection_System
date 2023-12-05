package com.abnormal.detection.controller.metadata;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;
import com.abnormal.detection.service.metadata.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metadata")
public class MetaDataController {

    private final MetaDataService metaDataService;

    @Autowired
    public MetaDataController(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }
/*
    @GetMapping("/all")
    public List<MetaData> getAllMetaData() {
        return metaDataService.getAllMetaData();
    }

 */
    @PostMapping("/create")
    public MetaData createMetaData(@RequestBody MetaData metaData) {
        return metaDataService.createMetaData(metaData);
    }

    @GetMapping("/allMetaData")
    public List<MetaData> getAllMetaDatas() {
        return metaDataService.getAllMetaDatas();
    }


    @GetMapping("/{metaDataId}")
    public MetaData getMetadataById(@PathVariable Long metaDataId) {
        return metaDataService.getMetadataById(metaDataId);
    }

    @GetMapping("/cctv/{cctvId}")
    public List<MetaData> getMetadataByCctvId(@PathVariable Long cctvId) {
        return metaDataService.getMetadataByCctvId(cctvId);
    }

    @GetMapping("/date-range")
    public List<MetaData> getMetadataByDateRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        return metaDataService.getMetadataByDateRange(startDate, endDate);
    }

    @GetMapping("/entity-type/{type}")
    public List<MetaData> getMetadataByEntityType(@PathVariable String type) {
        // EntityType은 Enum 타입으로 정의되어 있다고 가정
        return metaDataService.getMetadataByEntityType(EntityType.valueOf(type));
    }

    @GetMapping("/abnormal-type/{abnormalType}")
    public List<MetaData> getMetadataByAbnormalType(@PathVariable String abnormalType) {
        // AbnormalType은 Enum 타입으로 정의되어 있다고 가정
        return metaDataService.getMetadataByAbnormalType(AbnormalType.valueOf(abnormalType));
    }

    @GetMapping("/quality/{quality}")
    public List<MetaData> getMetadataByQuality(@PathVariable String quality) {
        // Quality는 Enum 타입으로 정의되어 있다고 가정
        return metaDataService.getMetadataByQuality(Quality.valueOf(quality));
    }

    @GetMapping("/video/{videoId}")
    public List<MetaData> getMetadataByVideoId(@PathVariable Long videoId) {
        return metaDataService.getMetadataByVideoId(videoId);
    }

    @GetMapping("/photo/{photoId}")
    public List<MetaData> getMetadataByPhotoId(@PathVariable Long photoId) {
        return metaDataService.getMetadataByPhotoId(photoId);
    }

    @PostMapping("/save")
    public MetaData saveMetadata(@RequestBody MetaData metaData) {
        return metaDataService.saveMetadata(metaData);
    }

    @PostMapping("/update")
    public MetaData updateMetadata(@RequestBody MetaData metaData) {
        return metaDataService.updateMetadata(metaData);
    }

    @DeleteMapping("/delete/{metaDataId}")
    public void deleteMetadata(@PathVariable Long metaDataId) {
        metaDataService.deleteMetadata(metaDataId);
    }

    @PutMapping("/update-info/{metaDataId}")
    public void updateMetadataInfo(@PathVariable Long metaDataId, @RequestBody MetaData updatedMetadata) {
        metaDataService.updateMetadataInfo(metaDataId, updatedMetadata);
    }
}
