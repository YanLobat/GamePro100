package com.hackday.controller;

import com.hackday.AppConfig;
import com.hackday.Constants;
import com.hackday.special.LoggingUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
@Transactional
public class AnswersControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSubmitAnon() throws Exception {
        Constants.loginRoleAnonymous();
        this.mockMvc.perform(get("/services/answers/submit?id=1&code=1231")
                .accept("application/json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testSubmitUser() throws Exception {
        Constants.loginRoleUser();

        MvcResult res = this.mockMvc.perform(get("/services/answers/submit?id=1&code=man.moveUp(); man.moveUp();")
                .accept("application/json")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().isOk())
                .andReturn();
        LoggingUtility.i(res.getResponse().getContentAsString());
    }

    @Test
    public void testGetByUserAndTask() throws Exception {
        Constants.loginRoleUser();

        MvcResult result = this.mockMvc.perform(get("/services/answers/getByUserAndTask?taskID=1")
                .accept("application/json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}