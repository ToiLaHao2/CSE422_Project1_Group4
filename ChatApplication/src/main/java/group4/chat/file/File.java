package group4.chat.file;

import group4.chat.domains.BaseEntity;

public class File extends BaseEntity {
    private String _name;
    private String _fileType;

    public File(String name, String fileType) {
        this._name = name;
        this._fileType = fileType;
    }
}

class Image extends File {
    public Image(String name) {
        super(name, "Image");
    }
}

class Video extends File {
    public Video(String name) {
        super(name, "Video");
    }
}

class Audio extends File {
    public Audio(String name) {
        super(name, "Audio");
    }
}
