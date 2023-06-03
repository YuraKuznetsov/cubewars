package org.geekhub.yurii.controller.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.news.Like;
import org.geekhub.yurii.service.news.LikeService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/news/{newsId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public void create(@PathVariable Integer newsId, Principal principal) {
        likeService.create(new Like(newsId, principal.getName()));
    }

    @DeleteMapping
    public void delete(@PathVariable Integer newsId, Principal principal) {
        likeService.delete(new Like(newsId, principal.getName()));
    }
}
