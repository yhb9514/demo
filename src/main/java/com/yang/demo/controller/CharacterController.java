package com.yang.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.demo.persistence.dto.ArmoryProfileDto;
import com.yang.demo.persistence.dto.CharacterDto;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/character")
public class CharacterController {

    @Value("${key}")
    private String key;

    @GetMapping("/all")
    public List<CharacterDto> showCharacters() {

        List<CharacterDto> characterDtoList = new ArrayList<>();

        try {
            String name = "양모뉙";
            name = URLEncoder.encode(name, "UTF-8");
            String testUrl = "https://developer-lostark.game.onstove.com/characters/" + name +"/siblings";
            URL url = new URL(testUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("authorization", key);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String json = "";
            String result;
            while ((result = br.readLine()) != null) {
                json += result;
            }
            conn.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);
            System.out.println(root.get(0));


            for (int i = 0; i < root.size(); i++) {
                CharacterDto characterDto = CharacterDto.builder()
                        .characterName(String.valueOf(root.get(i).get("CharacterName")).replaceAll("\"",""))
                        .serverName(String.valueOf(root.get(i).get("ServerName")).replaceAll("\"",""))
                        .characterLevel(Long.valueOf(String.valueOf(root.get(i).get("CharacterLevel"))))
                        .characterClassName(String.valueOf(root.get(i).get("CharacterClassName")).replaceAll("\"",""))
                        .itemAvgLevel(String.valueOf(root.get(i).get("ItemAvgLevel")).replaceAll("\"",""))
                        .itemMaxLevel(String.valueOf(root.get(i).get("ItemMaxLevel")).replaceAll("\"",""))
                        .build();

                characterDtoList.add(characterDto);
            }


            return characterDtoList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/detail")
    public Map<String, Object> showDetail(@RequestParam(value = "param" , required = false) String param) {
        System.out.println(param);
        if (param == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String name = param;
            name = URLEncoder.encode(name, "UTF-8");
            String testUrl = "https://developer-lostark.game.onstove.com/armories/characters/" + name;
            URL url = new URL(testUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("authorization", key);
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String json = "";
            String result;
            while ((result = br.readLine()) != null) {
                json += result;
            }
            conn.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);
            JsonNode armoryProfile = root.get("ArmoryProfile");
//            System.out.println(armoryProfile);
//            System.out.println(armoryProfile.get("Stats").get(0));

            String characterImage = String.valueOf(armoryProfile.get("CharacterImage")).replaceAll("\"","");
            Long expeditionLevel = Long.valueOf(String.valueOf(armoryProfile.get("ExpeditionLevel")));
            String pvpGradeName = String.valueOf(armoryProfile.get("PvpGradeName")).replaceAll("\"","");
            Long townLevel = Long.valueOf(String.valueOf(armoryProfile.get("TownLevel")).replaceAll("\"",""));
            String townName = String.valueOf(armoryProfile.get("TownName")).replaceAll("\"","");
            String title = String.valueOf(armoryProfile.get("Title")).replaceAll("\"","");
            String guildMemberGrade = String.valueOf(armoryProfile.get("GuildMemberGrade")).replaceAll("\"","");
            String guildName = String.valueOf(armoryProfile.get("GuildName")).replaceAll("\"","");
            Long usingSkillPoint = Long.valueOf(String.valueOf(armoryProfile.get("UsingSkillPoint")).replaceAll("\"",""));
            Long totalSkillPoint = Long.valueOf(String.valueOf(armoryProfile.get("TotalSkillPoint")).replaceAll("\"",""));
            String serverName = String.valueOf(armoryProfile.get("ServerName")).replaceAll("\"","");
            String characterName = String.valueOf(armoryProfile.get("CharacterName")).replaceAll("\"","");
            Long characterLevel = Long.valueOf(String.valueOf(armoryProfile.get("CharacterLevel")).replaceAll("\"",""));
            String characterClassName = String.valueOf(armoryProfile.get("CharacterClassName")).replaceAll("\"","");
            String itemAvgLevel = String.valueOf(armoryProfile.get("ItemAvgLevel")).replaceAll("\"","");
            String itemMaxLevel = String.valueOf(armoryProfile.get("ItemMaxLevel")).replaceAll("\"","");


            ArmoryProfileDto armoryProfileDto = ArmoryProfileDto.builder()
                    .characterImage(characterImage)
                    .expeditionLevel(expeditionLevel)
                    .pvpGradeName(pvpGradeName)
                    .townLevel(townLevel)
                    .townName(townName)
                    .title(title)
                    .guildMemberGrade(guildMemberGrade)
                    .guildName(guildName)
                    .usingSkillPoint(usingSkillPoint)
                    .totalSkillPoint(totalSkillPoint)
                    .stats(null)
                    .tendencies(null)
                    .serverName(serverName)
                    .characterName(characterName)
                    .characterLevel(characterLevel)
                    .characterClassName(characterClassName)
                    .itemAvgLevel(itemAvgLevel)
                    .itemMaxLevel(itemMaxLevel)
                    .build();



            resultMap.put("armoryProfile", armoryProfileDto);
            System.out.println();

            return resultMap;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
