public class RotateFileHandler extends FileHandler{

    private int maxFileSize;
    private int maxFiles;

    public RotateFileHandler(String fileName) {
        super(fileName);
    }

    public RotateFileHandler(String fileName,int maxFileSize) {
        super(fileName);
        this.maxFileSize = maxFileSize;
    }

    public RotateFileHandler(String fileName,int maxFileSize, int maxFiles) {
        super(fileName);
        this.maxFileSize = maxFileSize;
        this.maxFiles = maxFiles;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public int getMaxFiles() {
        return maxFiles;
    }

    public void setMaxFiles(int maxFiles) {
        this.maxFiles = maxFiles;
    }
}
