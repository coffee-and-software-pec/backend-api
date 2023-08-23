package com.coffeeandsoftware.api.controller;

import com.coffeeandsoftware.api.dto.ReturnDTO.ActivityDTO;
import com.coffeeandsoftware.api.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getActivitiesFromUser(@PathVariable String userId) {
        try {
            List<ActivityDTO> activityDTOList = activityService.getActivitiesFromUser(userId);
            return new ResponseEntity<>(
                    activityDTOList.stream().sorted(Comparator.comparing(ActivityDTO::getCreatedDate).reversed()),
            HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
