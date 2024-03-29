package group4.chat.domains;

import java.util.UUID;

public abstract class BaseEntity {
    private String _id;

    public String getId() {
        return _id;
    }

    public BaseEntity() {
        _id = UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this._id = id;
    }

}
