package com.cloud.publishing.web.client;

import com.cloud.publishing.common.dto.request.EmployeeRequest;
import com.cloud.publishing.common.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.common.dto.response.EmployeeResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeClient {
    @Value("${backend.url}")
    private String backendUrl;
    private final String EMPLOYEES_PATH = "/employees";
    private final RestTemplate restTemplate;

    @Autowired
    public EmployeeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeResponse> getAll() {
        String finalUrl = backendUrl + EMPLOYEES_PATH;
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(
                finalUrl,
                EmployeeResponse[].class).getBody())
        );
    }

    public EmployeeResponse get(int id) {
        String finalUrl = backendUrl + EMPLOYEES_PATH;
        return restTemplate.getForObject(finalUrl + "/" + id, EmployeeResponse.class);
    }

    public void add(EmployeeRequest request) {
        String finalUrl = backendUrl + EMPLOYEES_PATH;
        restTemplate.postForObject(finalUrl, request, EmployeeResponse.class);
    }

    public void update(int id, EmployeeUpdateRequest request) {
        String finalUrl = backendUrl + EMPLOYEES_PATH;
        restTemplate.put(finalUrl + "/" + id, request);
    }

    public void delete(int id) {
        String finalUrl = backendUrl + EMPLOYEES_PATH;
        restTemplate.delete(finalUrl + "/" + id);
    }
}