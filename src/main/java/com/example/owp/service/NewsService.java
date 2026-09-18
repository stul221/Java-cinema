package com.example.owp.service;

import com.example.owp.model.Booking;
import com.example.owp.model.News;
import com.example.owp.model.User;
import com.example.owp.repository.NewsRepository;
import com.example.owp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final FileService fileService;
    private final UserRepository userRepository;

    public NewsService(NewsRepository newsRepository, FileService fileService, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNews(Integer id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isEmpty()) {
            throw new RuntimeException("News not found");
        }
        News news = optionalNews.get();
        return news;
    }

    public News createNews(
            String title,
            String content,
            MultipartFile image
    ) throws IOException {
        String imageUrl = fileService.saveImage(image);

        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(userId).get();

        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setImage_url(imageUrl);
        news.setUser(user);

        return newsRepository.save(news);

    }

    public News updateNews(
            Integer id,
            String title,
            String content,
            MultipartFile image
    ) throws IOException {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isEmpty()) {
            throw new RuntimeException("News not found");
        }
        News news = optionalNews.get();

        news.setTitle(title);
        news.setContent(content);

        if (image != null && !image.isEmpty()) {
            if (news.getImage_url() != null) {
                fileService.deleteImage(news.getImage_url());
            }

            String imageUrl = fileService.saveImage(image);
            news.setImage_url(imageUrl);
        }

        return newsRepository.save(news);
    }

    public void deleteNews(Integer id) throws IOException {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isEmpty()) {
            throw new RuntimeException("News not found");
        }
        News news = optionalNews.get();

        if (news.getImage_url() != null) {
            fileService.deleteImage(news.getImage_url());
        }

        newsRepository.delete(news);
    }

}
