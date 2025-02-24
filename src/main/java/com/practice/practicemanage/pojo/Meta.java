package com.practice.practicemanage.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class Meta {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> roles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean affix;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean noCache;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String activeMenu;
}
