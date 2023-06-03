package org.geekhub.yurii.service.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.exception.DuplicatedLikeException;
import org.geekhub.yurii.model.news.Like;
import org.geekhub.yurii.repository.news.like.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public List<Like> getLikesForNews(Integer newsId) {
        return likeRepository.getLikesForNews(newsId);
    }

    public void create(Like like) {
        if (likeRepository.isDuplicatedLike(like)) {
            throw new DuplicatedLikeException("Can't save duplicated like");
        }

        likeRepository.create(like);
    }

    public void delete(Like like) {
        likeRepository.delete(like);
    }
}
