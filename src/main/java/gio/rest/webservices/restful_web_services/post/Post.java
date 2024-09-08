package gio.rest.webservices.restful_web_services.post;

import com.fasterxml.jackson.annotation.JsonView;
import gio.rest.webservices.restful_web_services.view.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue
    @JsonView(Views.Internal.class)
    private long id;

    private long authorId;

    private String content;

    public Post() {
    }

    public Post(long authorId, String content) {
        this.content = content;
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getAuthorId() {
        return authorId;
    }
}
