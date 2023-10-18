package com.yang.demo.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArmoryProfileDto {

    private String characterImage;

    private Long expeditionLevel;

    private String pvpGradeName;

    private Long townLevel;

    private String townName;

    private String title;

    private String guildMemberGrade;

    private String guildName;

    private Long usingSkillPoint;

    private Long totalSkillPoint;

    private StatsDto stats;

    private TendenciesDto tendencies;

    private String  serverName;

    private String characterName;

    private Long characterLevel;

    private String characterClassName;

    private String itemAvgLevel;

    private String itemMaxLevel;
}
