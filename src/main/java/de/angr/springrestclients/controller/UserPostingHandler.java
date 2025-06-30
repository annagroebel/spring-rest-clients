package de.angr.springrestclients.controller;

import de.angr.springrestclients.model.UserPosting;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserPostingHandler {

    private final UserPostingClient userPostingClient;

    public UserPostingHandler(UserPostingClient userPostingClient) {
        this.userPostingClient = userPostingClient;
    }

    public Mono<ServerResponse> getUserPost(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new UserPosting(1, 1, "foo", "bar")));
    }

    public Mono<ServerResponse> getAllUserPosts(ServerRequest request) {
        return userPostingClient.getPosts()
                .flatMap(posts -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(posts));
    }
}
