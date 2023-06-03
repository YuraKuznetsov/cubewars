package org.geekhub.yurii.service.news;

import org.geekhub.yurii.exception.DuplicatedLikeException;
import org.geekhub.yurii.model.news.Like;
import org.geekhub.yurii.repository.news.like.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    LikeRepository likeRepository;
    @InjectMocks
    LikeService likeService;

    @Test
    void throwException_whenDuplicatedLike() {
        when(likeRepository.isDuplicatedLike(any(Like.class)))
                .thenReturn(true);

        assertThrows(DuplicatedLikeException.class, () -> likeService.create(new Like()));
    }

    @Test
    void doesNotThrowException_whenNotDuplicatedLike() {
        when(likeRepository.isDuplicatedLike(any(Like.class)))
                .thenReturn(false);

        assertDoesNotThrow(() -> likeService.create(new Like()));
    }
}