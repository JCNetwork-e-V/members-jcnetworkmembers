package com.jcnetwork.members.model.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TimelineEntry {

    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date date;
    private String title;
    private String description;
    private String icon;
    private String iconColor;
    private String entryColor;
}
