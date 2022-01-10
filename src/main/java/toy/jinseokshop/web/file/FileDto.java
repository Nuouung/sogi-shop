package toy.jinseokshop.web.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class FileDto {

    private List<MultipartFile> files;

}
