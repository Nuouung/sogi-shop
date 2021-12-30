package toy.jinseokshop.web.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class FileFormDto {

    private MultipartFile file;
    private List<MultipartFile> files;

}
