package de.angr.springrestclients;

import de.angr.springrestclients.controller.UserPostingClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringRestClientsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringRestClientsApplication.class, args);
        UserPostingClient userPostingClient = context.getBean(UserPostingClient.class);
        // We need to block for the content here or the JVM might exit before the message is logged
        System.out.println(userPostingClient.getPost(1).block());
        System.out.println("\n");
        System.out.println(userPostingClient.getPosts().block());
        System.out.println("\n");
        System.out.println(userPostingClient.getPostNonBlocking(2));
        System.out.println("\n");
        System.out.println(userPostingClient.getUserPostNonBlocking(5).block());
    }

}
