package gio.rest.webservices.restful_web_services.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(Post post) {
        postRepository.save(postRepository.save(post));
    }

    public List<Post> retrievePostsForUser(long userId) {
        return postRepository.findAll().stream().filter(post -> post.getAuthorId() == userId).toList();
    }

}
