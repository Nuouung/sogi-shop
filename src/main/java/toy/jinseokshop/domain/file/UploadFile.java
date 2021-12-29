package toy.jinseokshop.domain.file;

import lombok.Getter;

@Getter
public class UploadFile {

    private String originalFileName;
    private String convertedFileName;

    protected UploadFile() {}

    public UploadFile(String originalFileName, String convertedFileName) {
        this.originalFileName = originalFileName;
        this.convertedFileName = convertedFileName;
    }
}
