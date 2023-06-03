package org.geekhub.yurii.service.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.news.comment.CommentCreateDTO;
import org.geekhub.yurii.dto.news.comment.CommentUpdateDTO;
import org.geekhub.yurii.exception.CommentLimitReachedException;
import org.geekhub.yurii.model.news.Comment;
import org.geekhub.yurii.repository.news.comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsForNews(Integer newsId) {
        return commentRepository.getCommentsForNews(newsId);
    }

    public Integer create(CommentCreateDTO commentCreateDTO) {
        if (isCommentLimitReached(commentCreateDTO)) {
            throw new CommentLimitReachedException("You've reached comment limit");
        }

        return commentRepository.create(commentCreateDTO);
    }

    private boolean isCommentLimitReached(CommentCreateDTO commentCreateDTO) {
        String username = commentCreateDTO.getUsername();
        int commentsCountInLastMinute = commentRepository.getCommentsCountInLastMinute(username);

        return commentsCountInLastMinute >= 3;
    }

    public void update(CommentUpdateDTO commentUpdateDTO) {
        commentRepository.update(commentUpdateDTO);
    }

    public void delete(Integer id) {
        commentRepository.delete(id);
    }

    public Optional<Comment> find(Integer id) {
        return commentRepository.find(id);
    }
}
