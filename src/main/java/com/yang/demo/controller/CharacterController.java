package com.yang.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.demo.persistence.dto.CharacterDto;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CharacterController {

    @Value("${key}")
    private String key;

    @GetMapping("/test")
    public List<CharacterDto> apiTest() {


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
}
