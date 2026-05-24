package com.cloud.publishing.web.client;

import com.cloud.publishing.common.dto.request.PublicationRequest;
import com.cloud.publishing.common.dto.response.PublicationGetDTO;
import com.cloud.publishing.common.dto.response.PublicationResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PublicationClient {
    @Value("${backend.url}")
    private String backendUrl;
    private final String PUBLICATIONS_PATH ="/publications";
    private final RestTemplate restTemplate;

    @Autowired
    public PublicationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PublicationGetDTO> getAll() {
        String finalUrl = backendUrl + PUBLICATIONS_PATH;
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(
                finalUrl,
                PublicationGetDTO[].class).getBody())
        );
    }

    public PublicationResponse get(int id) {
        String finalUrl = backendUrl + PUBLICATIONS_PATH;
        return restTemplate.getForObject(finalUrl + "/" + id, PublicationResponse.class);
    }

    public void add(PublicationRequest request) {
        String finalUrl = backendUrl + PUBLICATIONS_PATH;
        restTemplate.postForObject(finalUrl, request, PublicationResponse.class);
    }

    public void update(int id, PublicationRequest request) {
        String finalUrl = backendUrl + PUBLICATIONS_PATH;
        restTemplate.put(finalUrl + "/" + id, request);
    }

    public void delete(int id) {
        String finalUrl = backendUrl + PUBLICATIONS_PATH;
        restTemplate.delete(finalUrl + "/" + id);
    }
}