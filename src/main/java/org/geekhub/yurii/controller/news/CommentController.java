package org.geekhub.yurii.controller.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.news.comment.CommentCreateDTO;
import org.geekhub.yurii.dto.news.comment.CommentUpdateDTO;
import org.geekhub.yurii.exception.ItemNotFoundException;
import org.geekhub.yurii.model.news.Comment;
import org.geekhub.yurii.service.news.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/news/{newsId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getComments(@PathVariable Integer newsId) {
        return commentService.getCommentsForNews(newsId);
    }

    @PostMapping
    public Integer create(@Valid CommentCreateDTO commentCreateDTO) {
        return commentService.create(commentCreateDTO);
    }

    @GetMapping("/{id}")
    public Comment find(@PathVariable Integer id) {
        return commentService.find(id)
                .orElseThrow(() -> new ItemNotFoundException("Comment not found"));
    }

    @PutMapping("/{id}")
    public void update(@Valid CommentUpdateDTO commentUpdateDTO) {
        commentService.update(commentUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        commentService.delete(id);
    }
}
