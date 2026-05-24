package com.cloud.publishing.web.client;


import com.cloud.publishing.common.reference.Category;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CategoryClient {
    @Value("${backend.url}")
    private String backendUrl;
    private static final String CATEGORIES_PATH = "/categories";
    private final RestTemplate restTemplate;

    @Autowired
    public CategoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Category> getAll() {
        String finalUrl = backendUrl + CATEGORIES_PATH;
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(
                finalUrl,
                Category[].class))
        );
    }
}