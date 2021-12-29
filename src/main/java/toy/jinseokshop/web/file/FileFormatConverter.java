package toy.jinseokshop.web.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.domain.file.UploadFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileFormatConverter {

    // 파일 한개를 변환
    public UploadFile convert(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String convertedFilename = UUID.randomUUID().toString();
        return new UploadFile(originalFilename, convertedFilename);
    }

    // 파일 여러개를 변환
    public List<UploadFile> convert(List<MultipartFile> files) {
        List<UploadFile> fileList = new ArrayList<>();
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                fileList.add(new UploadFile(file.getOriginalFilename(), UUID.randomUUID().toString()));
            }
            return fileList;
        }
        return null;
    }
}
