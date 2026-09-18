package com.example.owp.controller;

import com.example.owp.model.News;
import com.example.owp.service.MovieService;
import com.example.owp.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/all")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public News getNews(@PathVariable Integer id) {
        return newsService.getNews(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public News createNews(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile image
            ) throws IOException {
        return newsService.createNews(title, content, image);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public News updateNews(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile image
    ) throws IOException {
        return newsService.updateNews(id, title, content, image);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Integer id) throws IOException {
        newsService.deleteNews(id);
    }


}
