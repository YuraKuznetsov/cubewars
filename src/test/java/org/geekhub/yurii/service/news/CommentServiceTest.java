package org.geekhub.yurii.service.news;

import org.geekhub.yurii.dto.news.comment.CommentCreateDTO;
import org.geekhub.yurii.exception.CommentLimitReachedException;
import org.geekhub.yurii.repository.news.comment.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    CommentService commentService;

    @Test
    void throwException_whenLimitIsReached() {
        when(commentRepository.getCommentsCountInLastMinute(anyString()))
                .thenReturn(3);

        CommentCreateDTO createDTO = new CommentCreateDTO();
        createDTO.setUsername("Cubewars");
        createDTO.setNewsId(10);
        createDTO.setContent("Bla bla bla");

        assertThrows(CommentLimitReachedException.class, () -> commentService.create(createDTO));
    }

    @Test
    void doesNotTrowException_whenLimitIsNotReached() {
        when(commentRepository.getCommentsCountInLastMinute(anyString()))
                .thenReturn(0);

        CommentCreateDTO createDTO = new CommentCreateDTO();
        createDTO.setUsername("Cubewars");
        createDTO.setNewsId(10);
        createDTO.setContent("Bla bla bla");

        assertDoesNotThrow(() -> commentService.create(createDTO));
    }
}