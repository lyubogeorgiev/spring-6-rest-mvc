package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID id;
    private String name;
    private Integer version;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
