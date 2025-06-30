package de.angr.springrestclients.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class UserPostingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserPostingHandler userPostingHandler) {
        return RouterFunctions
                .route(GET("/posts").and(accept(MediaType.APPLICATION_JSON)), userPostingHandler::getAllUserPosts)
                .andRoute(GET("/posts/{userPostId}").and(accept(MediaType.APPLICATION_JSON)), userPostingHandler::getUserPost);
    }

}
