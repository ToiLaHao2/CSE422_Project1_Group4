package group4.chat.file;

public class File {
	private String name;
    private String fileType;

    public File(String name, String fileType) {
        this.name = name;
        this.fileType = fileType;
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

class Audio extends File{
	public Audio(String name) {
		super(name, "Audio");
	}
}
