package org.geekhub.yurii.service.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.news.NewsCreateDTO;
import org.geekhub.yurii.dto.news.NewsUpdateDTO;
import org.geekhub.yurii.model.news.Comment;
import org.geekhub.yurii.model.news.Like;
import org.geekhub.yurii.model.news.News;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.repository.news.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final LikeService likeService;
    private final CommentService commentService;

    public Optional<News> find(Integer id) {
        Optional<News> newsOptional = newsRepository.find(id);

        newsOptional.ifPresent(news -> {
            setCommentsForNews(news);
            setLikesForNews(news);
        });

        return newsOptional;
    }

    public Integer create(NewsCreateDTO newsCreateDTO) {
        return newsRepository.create(newsCreateDTO);
    }

    public void update(NewsUpdateDTO newsUpdateDTO) {
        newsRepository.update(newsUpdateDTO);
    }

    public void delete(Integer id) {
        newsRepository.delete(id);
    }

    public Page<News> getPageNews(Pageable pageable) {
        List<News> pageNews = newsRepository.getNewsList(pageable);

        for (News news : pageNews) {
            setCommentsForNews(news);
            setLikesForNews(news);
        }

        boolean hasNext = newsRepository.getCount() > pageable.getOffset() + pageable.getLimit();

        return new Page<>(pageNews, hasNext);
    }

    private void setCommentsForNews(News news) {
        List<Comment> comments = commentService.getCommentsForNews(news.getId());
        news.setComments(comments);
    }

    private void setLikesForNews(News news) {
        List<Like> likes = likeService.getLikesForNews(news.getId());
        news.setLikes(likes);
    }
}
