package de.angr.springrestclients.controller;

import de.angr.springrestclients.model.SampleResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
public class SampleRestController {

    private final RestClient restClient;

    public SampleRestController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/posts/{postId}")
    public SampleResponse getDistinctPost(@PathVariable Long postId) {
        return restClient
                .get()
                .uri("/posts/" + postId)
                .retrieve()
                .body(SampleResponse.class);
    }

    @GetMapping("/posts")
    public List<SampleResponse> getPosts() {
        return restClient
                .get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @GetMapping("/postExists/{postId}")
    public boolean postExists(@PathVariable Long postId) {
        HttpStatusCode statusCode = restClient
                .get()
                .uri("/posts/" + postId)
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();
        return statusCode.value() == 200;
    }

    @PostMapping("/createPost")
    public SampleResponse publishPost(@RequestBody Map<String, Object> request) {
        return restClient
                .post()
                .uri("/posts")
                .body(request)
                .retrieve()
                .body(SampleResponse.class);
    }

    @PostMapping("/postCreated")
    public boolean postCreated(@RequestBody Map<String, Object> request) {
        ResponseEntity<Void> response = restClient
                .post()
                .uri("/posts")
                .body(request)
                .retrieve()
                .toBodilessEntity();
        return response.getStatusCode().value() == 201;
    }

    @DeleteMapping("/deletePost/{postId}")
    public boolean deletePost(@PathVariable Long postId) {
        HttpStatusCode statusCode = restClient
                .delete()
                .uri("posts/" + postId)
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();
        return statusCode.value() == 200;
    }

    @PutMapping("/putPost/{postId}")
    public SampleResponse putPost(@PathVariable Long postId, @RequestBody Map<String, Object> request) {
        return restClient
                .put()
                .uri("/posts/" + postId)
                .body(request)
                .retrieve()
                .body(SampleResponse.class);
    }
}


