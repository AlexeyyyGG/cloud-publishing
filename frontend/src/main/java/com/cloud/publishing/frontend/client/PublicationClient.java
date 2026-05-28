package com.cloud.publishing.frontend.client;

import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.common.dto.request.PublicationRequest;
import com.cloud.publishing.common.dto.response.PublicationGetDTO;
import com.cloud.publishing.common.dto.response.PublicationResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PublicationClient {
    @Value("${backend.url}")
    private String backendUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public PublicationClient(@Qualifier("apiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PublicationGetDTO> getAll() {
        String finalUrl = backendUrl + Urls.PUBLICATIONS;
        return Arrays.asList(Objects.requireNonNull(
                restTemplate.getForEntity(finalUrl, PublicationGetDTO[].class).getBody())
        );
    }

    public PublicationResponse get(int id) {
        String finalUrl = backendUrl + Urls.PUBLICATIONS;
        return restTemplate.getForObject(finalUrl + "/" + id, PublicationResponse.class);
    }

    public void add(PublicationRequest request) {
        String finalUrl = backendUrl + Urls.PUBLICATIONS;
        restTemplate.postForObject(finalUrl, request, PublicationResponse.class);
    }

    public void update(int id, PublicationRequest request) {
        String finalUrl = backendUrl + Urls.PUBLICATIONS;
        restTemplate.put(finalUrl + "/" + id, request);
    }

    public void delete(int id) {
        String finalUrl = backendUrl + Urls.PUBLICATIONS;
        restTemplate.delete(finalUrl + "/" + id);
    }
}