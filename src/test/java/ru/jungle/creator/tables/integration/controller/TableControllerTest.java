package ru.jungle.creator.tables.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.jungle.creator.tables.dto.response.ResultColumns;
import ru.jungle.creator.tables.integration.IntegrationTestBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class TableControllerTest extends IntegrationTestBase {

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getTables() throws Exception {
        List<ResultColumns> expectDesc = List.of(
                new ResultColumns("customers", "id", "integer"),
                new ResultColumns("customers", "username", "character varying")
        );
        Map<String, List<ResultColumns>> expectResult = new HashMap<>();
        expectResult.put("customers", expectDesc);
        String expectedJson = new ObjectMapper()
                .writeValueAsString(expectResult);

        mockMvc.perform(post("/api/table/desc")
                        .param("tableName", "customers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(
                        MockMvcResultMatchers
                                .content()
                                .json(expectedJson)
                );
    }

}