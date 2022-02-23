package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.BookRequestData;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthenticationService authenticationService;

    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = 100L;
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.KiNUK70RDCTWeRMqfN6YY_SAkkb8opFsAh_fwAntt4";


    User user = User.builder().id(EXISTED_ID).build();
    Book book = Book.builder().id(EXISTED_ID).isbn("9788960773431").build();
    Review review = Review.builder()
            .id(EXISTED_ID)
            .user(user)
            .book(book)
            .title("제목")
            .content("내용")
            .score(BigDecimal.valueOf(4.5))
            .point(5)
            .build();

    @BeforeEach
    void setUp() {
        given(reviewService.createReview(any(User.class), any(Book.class), any(ReviewRequestData.class))).will(invocation -> {
            return review;
        });

        given(reviewService.getReviewById(EXISTED_ID)).will(invocation -> {
            return review;
        });

        given(reviewService.getReviewById(NOT_EXISTED_ID))
                .willThrow(new ReviewNotFoundException(NOT_EXISTED_ID));

        given(userService.getUser(EXISTED_ID))
                .willReturn(user);

        given(bookService.getOrCreateBook(any(BookRequestData.class)))
                .willReturn(book);

        given(authenticationService.parseToken(VALID_TOKEN))
                .willReturn(EXISTED_ID);
    }

    @Test
    public void 올바른_정보_리뷰생성_성공() throws Exception {
        mvc.perform(post("/reviews")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"book\": {\n" +
                                "        \"isbn\": \"9788960771468\",\n" +
                                "        \"name\": \"토비의 스프링3\",\n" +
                                "        \"writer\": \"이일민\",\n" +
                                "        \"imgUrl\": \"https://bookthumb-phinf.pstatic.net/cover/070/065/07006516.jpg\"\n" +
                                "    },\n" +
                                "    \"title\": \"스프링 개념의 바이블\",\n" +
                                "    \"content\": \"좋아용\",\n" +
                                "    \"score\": \"3.5\",\n" +
                                "    \"point\": \"5\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }


    @Test
    public void 올바르지않은_정보_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"title\": \"스프링 개념의 바이블\",\n" +
                                "    \"content\": \"좋아용\",\n" +
                                "    \"score\": \"8\",\n" +
                                "    \"point\": \"5\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 평점범위초과_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"book\": {\n" +
                                "        \"isbn\": \"9788960771468\",\n" +
                                "        \"name\": \"토비의 스프링3\",\n" +
                                "        \"writer\": \"이일민\",\n" +
                                "        \"imgUrl\": \"https://bookthumb-phinf.pstatic.net/cover/070/065/07006516.jpg\"\n" +
                                "    },\n" +
                                "    \"title\": \"스프링 개념의 바이블\",\n" +
                                "    \"content\": \"좋아용\",\n" +
                                "    \"score\": \"9.0\",\n" +
                                "    \"point\": \"5\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ISBN형식위반_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"book\": {\n" +
                                "        \"isbn\": \"978898\",\n" +
                                "        \"name\": \"토비의 스프링3\",\n" +
                                "        \"writer\": \"이일민\",\n" +
                                "        \"imgUrl\": \"https://bookthumb-phinf.pstatic.net/cover/070/065/07006516.jpg\"\n" +
                                "    },\n" +
                                "    \"title\": \"스프링 개념의 바이블\",\n" +
                                "    \"content\": \"좋아용\",\n" +
                                "    \"score\": \"3.5\",\n" +
                                "    \"point\": \"5\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인X_리뷰생성_실패() throws Exception {
        mvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"book\": {\n" +
                                "        \"isbn\": \"9788960771468\",\n" +
                                "        \"name\": \"토비의 스프링3\",\n" +
                                "        \"writer\": \"이일민\",\n" +
                                "        \"imgUrl\": \"https://bookthumb-phinf.pstatic.net/cover/070/065/07006516.jpg\"\n" +
                                "    },\n" +
                                "    \"title\": \"스프링 개념의 바이블\",\n" +
                                "    \"content\": \"좋아용\",\n" +
                                "    \"score\": \"3.5\",\n" +
                                "    \"point\": \"5\"\n" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

}
