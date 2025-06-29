package de.angr.springrestclients.controller;

import de.angr.springrestclients.model.UserPosting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserPostingRestController {

    private final RestTemplate restTemplate;

    public UserPostingRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/posts")
    public List<UserPosting> getUserPostings() {
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", List.class);
    }

    @GetMapping("/posts/{userPostId}")
    public UserPosting getUserPosting(@PathVariable Long userPostId) {
        try {
            ResponseEntity<UserPosting> userPosting = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/" + userPostId, UserPosting.class);
            return userPosting.getStatusCode().value() == 200 ? userPosting.getBody() : null;
        } catch (Exception e) {
            System.out.println("User posting was not found!");
            return null;
        }
    }

    @PostMapping("/posts")
    public UserPosting postUserPosting(@RequestBody UserPosting userPosting) {
        try {
            return restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", userPosting, UserPosting.class);
        } catch (Exception e) {
            System.out.println("User posting could not be created!");
            return null;
        }
    }

    @PostMapping("/posts/1")
    public UserPosting postUserPosting() {
        UserPosting userPosting = new UserPosting(1, 1, "foo", "bar");
        ResponseEntity<UserPosting> response = restTemplate.exchange(
                "https://jsonplaceholder.typicode.com/posts",
                HttpMethod.POST,
                new HttpEntity<>(userPosting, new HttpHeaders()),
                UserPosting.class);
        return response.getBody();
    }

    @DeleteMapping("/posts/{userPostId}")
    public void deleteUserPosting(@PathVariable Long userPostId) {
        try {
            restTemplate.delete("https://jsonplaceholder.typicode.com/posts/" + userPostId);
        } catch (Exception e) {
            System.out.println("User posting could not be deleted!");
        }
    }

}
