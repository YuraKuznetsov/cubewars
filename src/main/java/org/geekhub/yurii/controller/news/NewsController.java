package org.geekhub.yurii.controller.news;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.news.NewsCreateDTO;
import org.geekhub.yurii.dto.news.NewsUpdateDTO;
import org.geekhub.yurii.exception.ItemNotFoundException;
import org.geekhub.yurii.model.news.News;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.service.news.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping(produces = {"text/html", "application/xhtml+xml"})
    public String getTemplate() {
        return "news";
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public Page<News> getPageNews(@Valid Pageable pageable) {
        return newsService.getPageNews(pageable);
    }

    @PostMapping
    @ResponseBody
    public Integer create(@Valid NewsCreateDTO newsCreateDTO) {
        return newsService.create(newsCreateDTO);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public News find(@PathVariable Integer id) {
        return newsService.find(id)
                .orElseThrow(() -> new ItemNotFoundException("News not found"));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void update(@Valid NewsUpdateDTO newsUpdateDTO) {
        newsService.update(newsUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Integer id) {
        newsService.delete(id);
    }
}
