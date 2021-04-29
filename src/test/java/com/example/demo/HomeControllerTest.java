package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)  //针对HomeController的web测试
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;  //注入MockMvc  mock：模拟

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))  //发起对“/”的GET
                .andExpect(status().isOk())   //期望得到HTTP 200
                .andExpect(view().name("home"))  //期望得到home视图
                .andExpect(content().string(
                        containsString("Welcome to...")
                ));  //期望包含"Welcome to..." content()是MockMvc的静态方法
    }
}
