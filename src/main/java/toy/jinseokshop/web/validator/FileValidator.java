package toy.jinseokshop.web.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Slf4j
public class FileValidator {

    private final String[] imageExtension = {".jpg", ".png", ".jpeg", ".bmg", ".gif"};

    public void isImageValidate(List<MultipartFile> files, BindingResult bindingResult) {
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            if (!file.isEmpty()) {
                int pos = originalFilename.lastIndexOf(".");
                String extension = originalFilename.substring(pos);// .xxx
                if (!isImageFile(extension)) {
                    bindingResult.addError(new ObjectError("fileError", "사진 파일만 첨부 가능합니다."));
                }
            }
        }
    }

    /**
     * 제공된 파일의 확장자가 이미지 확장자인 경우 true 반환
     */
    private boolean isImageFile(String extension) {
        return PatternMatchUtils.simpleMatch(imageExtension, extension);
    }
}
