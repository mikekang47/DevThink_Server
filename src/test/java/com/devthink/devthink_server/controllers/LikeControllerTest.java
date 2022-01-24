package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.LikeService;
import com.devthink.devthink_server.domain.Like;
import com.devthink.devthink_server.dto.LikeData;
import com.github.dozermapper.core.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    LikeService likeService;



    @BeforeEach
    void setUp() {
        Like like = Like.builder()
                .id(10L)
                .postId(13L)
                .userId(10L)
                .build();

        given(likeService.getLike(10L)).willReturn(like);
    }

    @Test
    void 올바른_userId와_postId로_요청을_하는경우() throws Exception {
        mvc.perform(get("/likes/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"10\",\"postId\":\"13\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"userId\":10")))
                .andExpect(content().string(containsString("\"postId\":13")));

    }

}
