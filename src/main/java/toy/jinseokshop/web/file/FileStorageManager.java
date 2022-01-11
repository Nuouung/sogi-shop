package toy.jinseokshop.web.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.domain.file.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorageManager {

    @Value("${file.dir}")
    private String fileDir;

    /**
     * 외부로 공개되는 유일한 메소드
     * 수량의 경우의 수가 많은 파일들을 일괄 변환
     */
    public List<File> store(List<MultipartFile> files) throws IOException {
        List<File> fileList = new ArrayList<>();
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    fileList.add(this.store(file));
                }
            }
            return fileList;
        }
        return new ArrayList<>();
    }

    /** 물리적인 저장장치에 파일 저장 */
    public String getFullPath(String convertedFilename) {
        return fileDir + convertedFilename;
    }

    /** 개별 파일을 변환 */
    private File store(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String convertedFilename = createConvertedFileName(originalFilename);
        file.transferTo(new java.io.File(getFullPath(convertedFilename)));
        return new File(originalFilename, convertedFilename);
    }

    /** 물리적인 저장장치에 저장되는 새로운 파일 이름 */
    private String createConvertedFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String extension = extractExtensionName(originalFilename);
        return uuid + "." + extension;
    }

    /** 원래 파일에서 확장자 추출 */
    private String extractExtensionName(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
