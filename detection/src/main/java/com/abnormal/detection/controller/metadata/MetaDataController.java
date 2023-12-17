package com.abnormal.detection.controller.metadata;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.metadata.AbnormalType;
import com.abnormal.detection.domain.metadata.EntityType;
import com.abnormal.detection.domain.metadata.MetaData;
import com.abnormal.detection.domain.metadata.Quality;
import com.abnormal.detection.service.metadata.MetaDataService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

//*******searchMetaDatas*******
    @PostMapping("/search")
    public List<MetaData> searchMetaDatas(@RequestBody List<Map<String, String>> searchRequests) {
        List<MetaData> result = new ArrayList<>();

        for (Map<String, String> searchRequest : searchRequests) {
            //1
            String foundTimeStr = searchRequest.get("foundTime");
            Date foundTime = null;
            if (foundTimeStr != null && !foundTimeStr.isEmpty()) {

                foundTime = parseDate(foundTimeStr);
            }
            //2
            String entityFoundTimeStr = searchRequest.get("entityFoundTime");
            Date entityFoundTime = null;
            if (entityFoundTimeStr != null && !entityFoundTimeStr.isEmpty()) {

                entityFoundTime = parseDate(entityFoundTimeStr);
            }
            //3
            String cctvIdStr = searchRequest.get("cctvId");
            Long cctvId = (cctvIdStr != null && !cctvIdStr.isEmpty()) ? Long.valueOf(cctvIdStr) : null;
            //4
            String abnormalTypeStr = searchRequest.get("abnormalType");
            AbnormalType abnormalType = null;
            if (abnormalTypeStr != null && !abnormalTypeStr.isEmpty()) {
                abnormalType = convertToAbnormalType(abnormalTypeStr);
            }

            List<MetaData> metaDataList = metaDataService.searchMetaDatasByOptions(foundTime, entityFoundTime, cctvId, abnormalType);
            result.addAll(metaDataList);
        }

        return result;
    }

    // parsing String to Date(format)
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // AbnormalType
    private AbnormalType convertToAbnormalType(String abnormalTypeStr) {

        return AbnormalType.valueOf(abnormalTypeStr);
    }
//*******searchMetaDatas*******



//*******date-range*******
    @GetMapping("/date-range")
    public List<MetaData> getMetadataByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        // 서비스 레이어에서 String을 Date로 변환
        Date startDateParsed = dateParseDate(startDate);
        Date endDateParsed = dateParseDate(endDate);

        return metaDataService.getMetadataByDateRange(startDateParsed, endDateParsed);
    }

    private Date dateParseDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return null;
        }
    }
//*******date-range*******




    //*******searchMetaDatas*******
    @PostMapping("/Legend")
    public List<MetaData> searchLegend(@RequestBody List<Map<String, String>> searchRequests) {
        List<MetaData> result = new ArrayList<>();

        for (Map<String, String> searchRequest : searchRequests) {
            String startDateStr = searchRequest.get("startDate");
            Date startDate = null;
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = dateParseDate(startDateStr);
            }

            String endDateStr = searchRequest.get("endDate"); // 수정: "endDate"로 변경
            Date endDate = null;
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = dateParseDate(endDateStr);
            }

            String cctvIdStr = searchRequest.get("cctvId");
            Long cctvId = (cctvIdStr != null && !cctvIdStr.isEmpty()) ? Long.valueOf(cctvIdStr) : null;

            String abnormalTypeStr = searchRequest.get("abnormalType");
            AbnormalType abnormalType = null;
            if (abnormalTypeStr != null && !abnormalTypeStr.isEmpty()) {
                abnormalType = convertToAbnormalType(abnormalTypeStr);
            }

            List<MetaData> metaDataList = metaDataService.searchLegendByOptions(startDate, endDate, cctvId, abnormalType);
            result.addAll(metaDataList);
        }

        return result;
    }
/*
    // parsing String to Date(format)
    private Date parseDate2(String dateString2) {
        try {
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            return dateFormat2.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // AbnormalType
    private AbnormalType convertToAbnormalType2(String abnormalTypeStr2) {

        return AbnormalType.valueOf(abnormalTypeStr2);
    }

 */
//*******searchMetaDatas*******


    @GetMapping("/{metaDataId}")
    public MetaData getMetadataById(@PathVariable Long metaDataId) {
        return metaDataService.getMetadataById(metaDataId);
    }

    @GetMapping("/cctv/{cctvId}")
    public List<MetaData> getMetadataByCctvId(@PathVariable Long cctvId) {
        return metaDataService.getMetadataByCctvId(cctvId);
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
/*
    @PostMapping("/update")
    public MetaData updateMetadata(@RequestBody MetaData metaData) {
        return metaDataService.updateMetadata(metaData);
    }

 */
    @PostMapping("/update")
    public MetaData updateMetadata(@RequestBody MetaData metaData) {
        try {
            return metaDataService.updateMetadata(metaData);
        } catch (OptimisticLockException e) {
            // Optimistic Locking 예외 처리
            // 클라이언트에게 충돌이 발생했음을 알리거나, 다른 처리를 수행할 수 있습니다.
            e.printStackTrace();
            return null; // 또는 적절한 응답 반환
        }
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
