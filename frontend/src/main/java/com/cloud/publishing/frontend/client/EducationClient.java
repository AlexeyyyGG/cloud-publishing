package com.cloud.publishing.frontend.client;

import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.model.employee.Education;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EducationClient {
    @Value("${backend.url}")
    private String backendUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public EducationClient(@Qualifier("apiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Education> getAll() {
        String finalUrl = backendUrl + Urls.EDUCATIONS;
        return Arrays.asList(Objects.requireNonNull(
                restTemplate.getForObject(finalUrl, Education[].class)));
    }
}
