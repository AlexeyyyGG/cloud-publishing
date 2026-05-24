package com.cloud.publishing.web.client;

import com.cloud.publishing.common.reference.Education;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EducationClient {
    @Value("${backend.url}")
    private String backendUrl;
    private static final String EDUCATIONS_PATH = "/educations";
    private final RestTemplate restTemplate;

    @Autowired
    public EducationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Education> getAll() {
        String finalUrl = backendUrl + EDUCATIONS_PATH;
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(
                finalUrl,
                Education[].class))
        );
    }
}
