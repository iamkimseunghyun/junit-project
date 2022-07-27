package io.laaf.junitproject.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    @Test
    @DisplayName("데이터준비")
    void 데이터준비() {
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        // when (테스트 실행)
        bookRepository.save(book);
    }

    // 1. 책 등록
    @Test
    @DisplayName("책등록_test")
    void 책등록_Test() {
        // given (데이터 준비)
        String title = "junit";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 2. 책 목록 보기
    @Test
    @DisplayName("책목록보기")
    void 책목록보기() {
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertEquals(title, books.get(0).getTitle());
        assertEquals(author, books.get(0).getAuthor());

    }

    // 3. 책 한건 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책한건보기")
    void 책한건보기() {
        String title = "junit";
        String author = "겟인데어";

        // when (테스트 실행)
        Book bookPS = bookRepository.findById(1L).get();
        System.out.println(bookPS.getId());

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }

    // 4. 책 수정

    // 5. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책삭제")
    void 책삭제() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent());
    }
}