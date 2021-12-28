package toy.jinseokshop.fileUpload.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class UploadFile {

    private String uploadFileName;
    private String storageFileName;

    protected UploadFile() {
    }

    public UploadFile(String uploadFileName, String storageFileName) {
        this.uploadFileName = uploadFileName;
        this.storageFileName = storageFileName;
    }
}
