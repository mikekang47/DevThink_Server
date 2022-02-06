package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1L).build();
        Book book = Book.builder().isbn(1).build();

        Review review = Review.builder()
                .id(5L)
                .user(user)
                .book(book)
                .content("유용한 책입니다.")
                .build();
        review.toReviewResponseData();

        given(reviewService.getReviewById(5L)).willReturn(review);
    }
    
    @Test
    public void 올바른_정보_리뷰생성_성공() throws Exception {
        mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"userId\": \"13\",\n" +
                                "    \"book\" : {\n" +
                                "        \"isbn\" : 10,\n" +
                                "        \"name\" : \"자바 기초\",\n" +
                                "        \"writer\" : \"이비\",\n" +
                                "        \"imgUrl\" : \"www.img.com\"\n" +
                                "    },\n" +
                                "    \"content\" : \"내용~~~~~~~\",\n" +
                                "    \"score\" : \"5.0\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void 내용없이_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"userId\": \"13\",\n" +
                                "    \"book\" : {\n" +
                                "        \"isbn\" : 10,\n" +
                                "        \"name\" : \"자바 기초\",\n" +
                                "        \"writer\" : \"이비\",\n" +
                                "        \"imgUrl\" : \"www.img.com\"\n" +
                                "    },\n" +
                                "    \"content\" : \"\"\n" +
                                "    \"score\" : \"5.0\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void 책정보_누락시_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"userId\": \"13\",\n" +
                                "    \"book\" : {\n" +
                                "        \"isbn\" : 10,\n" +
                                "        \"writer\" : \"이비\",\n" +
                                "        \"imgUrl\" : \"www.img.com\"\n" +
                                "    },\n" +
                                "    \"content\" : \"내용~~~~~~~\",\n" +
                                "    \"score\" : \"5.0\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 평점없이_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"userId\": \"13\",\n" +
                                "    \"book\" : {\n" +
                                "        \"isbn\" : 10,\n" +
                                "        \"name\" : \"자바 기초\",\n" +
                                "        \"writer\" : \"이비\",\n" +
                                "        \"imgUrl\" : \"www.img.com\"\n" +
                                "    },\n" +
                                "    \"content\" : \"내용~~~~~~~\",\n"+
                                "}"))
                .andExpect(status().isBadRequest());
    }

}
