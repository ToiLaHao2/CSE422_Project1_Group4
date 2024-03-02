package group4.chat.domains;

import java.util.UUID;

public abstract class BaseEntity {
    private String id;

    public String getId() {
        return id;
    }

    public BaseEntity() {
        id = UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

}
