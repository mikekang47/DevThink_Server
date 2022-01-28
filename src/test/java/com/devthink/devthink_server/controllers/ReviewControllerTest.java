package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;

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
        review.toReviewResponseDto();

        given(reviewService.getReviewById(5L)).willReturn(review);
    }

    @Test
    public void review_create_success() throws Exception {
        this.mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \n\"bookIsbn\": 1, " +
                                "\n\"content\": \"유용한 책입니다.\", \n\"score\": 4.04}"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void review_get_success() throws Exception {
        this.mvc.perform(get("/reviews/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
