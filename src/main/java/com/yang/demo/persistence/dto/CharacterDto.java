package com.yang.demo.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CharacterDto {

    private String characterName;

    private String serverName;

    private Long characterLevel;

    private String characterClassName;

    private String itemAvgLevel;

    private String itemMaxLevel;

}
