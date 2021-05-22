package com.jcnetwork.members.controller.rest;

import com.jcnetwork.members.exception.ItemNotFoundException;
import com.jcnetwork.members.model.data.InternalMessage;
import com.jcnetwork.members.model.data.TimelineEntry;
import com.jcnetwork.members.model.data.consultancy.Consultancy;
import com.jcnetwork.members.model.dto.InternalMessageDto;
import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timeline")
public class TimelineRESTController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserTimeline(@PathVariable("id") String userId, Pageable pageable) {

        Page<TimelineEntry> timeline = userService.getTimelineByUserId(userId, pageable);

        return ResponseEntity.ok(timeline);
    }
}
