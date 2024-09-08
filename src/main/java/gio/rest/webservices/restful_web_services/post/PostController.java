package gio.rest.webservices.restful_web_services.post;

import com.fasterxml.jackson.annotation.JsonView;
import gio.rest.webservices.restful_web_services.view.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/create-post")
    @JsonView(Views.Public.class)
    public ResponseEntity<Object> createPost(
            @RequestBody Post post
    ) {
        postService.createPost(post);
        return ResponseEntity.created(null).build();
    }

    @GetMapping(value = "/{userId}/posts")
    public ResponseEntity<List<Post>> getPostsForUser(@PathVariable long userId) {
        return ResponseEntity.ok(postService.retrievePostsForUser(userId));
    }
}
