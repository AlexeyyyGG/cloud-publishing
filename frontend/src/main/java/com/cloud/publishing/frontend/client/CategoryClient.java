package com.cloud.publishing.frontend.client;


import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.model.publication.Category;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CategoryClient {
    @Value("${backend.url}")
    private String backendUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public CategoryClient(@Qualifier("apiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Category> getAll() {
        String finalUrl = backendUrl + Urls.CATEGORIES;
        return Arrays.asList(Objects.requireNonNull(
                restTemplate.getForObject(finalUrl, Category[].class)));
    }
}