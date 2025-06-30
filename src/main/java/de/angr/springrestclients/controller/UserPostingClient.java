package de.angr.springrestclients.controller;

import de.angr.springrestclients.model.UserPosting;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserPostingClient {

    private final WebClient webClient;

    public UserPostingClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<String>> getPosts() {
        return this.webClient.get().uri("/posts").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserPosting.class)
                .map(UserPosting::getTitle)
                .collectList();
    }

    public Mono<String> getPost(@RequestParam int userPostId) {
        return this.webClient.get().uri("/posts/{userPostId}", userPostId).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserPosting.class)
                .map(UserPosting::getTitle);
    }

    public Map<String, Object> getPostNonBlocking(@RequestParam int userPostId) {

        Mono<List<String>> allUserPosts = getPosts();
        Mono<String> distinctPost = getPost(userPostId);

        return Mono.zip(distinctPost, allUserPosts, (singlePost, allPosts) -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("singlePost", singlePost);
                    map.put("allPosts", allPosts);
                    return map;
                })
                .block();
    }

    public Mono<UserPosting> getUserPostNonBlocking(int userPostId) {
        return this.webClient.get()
                .uri("/posts/{userPostId}", userPostId)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(UserPosting.class);
                    }
                    else {
                        // Turn to error
                        return response.createError();
                    }
                });
    }
}
