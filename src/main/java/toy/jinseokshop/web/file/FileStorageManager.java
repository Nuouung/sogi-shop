package toy.jinseokshop.web.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.domain.file.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorageManager {

    @Value("${file.dir}")
    private String fileDir;

    // 파일 한개를 변환
    public UploadFile store(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;
        String originalFilename = file.getOriginalFilename();
        String convertedFilename = UUID.randomUUID().toString();
        file.transferTo(new File(getFullPath(convertedFilename)));
        return new UploadFile(originalFilename, convertedFilename);
    }

    // 파일 여러개를 변환
    public List<UploadFile> store(List<MultipartFile> files) throws IOException {
        List<UploadFile> fileList = new ArrayList<>();
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String uuid = UUID.randomUUID().toString();
                    fileList.add(new UploadFile(file.getOriginalFilename(), uuid));
                    file.transferTo(new File(getFullPath(uuid)));
                }
            }
            return fileList;
        }
        return null;
    }

    public String getFullPath(String convertedFilename) {
        return fileDir + convertedFilename;
    }
}
