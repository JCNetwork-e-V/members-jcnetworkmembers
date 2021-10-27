package com.jcnetwork.members.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DataCompletionForm {

    private Map<String, Object> openDataFields = new HashMap<>();
}
