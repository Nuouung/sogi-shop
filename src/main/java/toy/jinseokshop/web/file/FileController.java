package toy.jinseokshop.web.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import toy.jinseokshop.domain.file.FileService;
import toy.jinseokshop.domain.file.FileViewDto;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

    private final FileStorageManager fileStorageManager;

    @GetMapping("/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long boardId) throws MalformedURLException {
        FileViewDto fileViewDto = fileService.findByBoardId(boardId);
        String originalFileName = fileViewDto.getFile().getOriginalFileName();
        String convertedFileName = fileViewDto.getFile().getConvertedFileName();

        UrlResource resource = new UrlResource("file:" + fileStorageManager.getFullPath(convertedFileName));

        log.info("=== 첨부파일 다운로드 [ {} ] ===", originalFileName);

        String encodedUploadFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStorageManager.getFullPath(filename));
    }

}
