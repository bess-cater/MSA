package com.msa.accounts.DTO;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

//record! --> data carrier: read, but not write (all final, no setters)

@ConfigurationProperties(prefix="accounts")
@Setter
@Getter
public class AccContactDTO{
    private String message;
    private Map<String, String> contactDetails;
    private List<String> skills;
}


