package toy.jinseokshop.fileUpload.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.fileUpload.service.MusicService;
import toy.jinseokshop.web.login.SessionConst;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class MusicController {

    @Value("${file.dir}")
    private String fileDir;
    private final MusicService musicService;

    @GetMapping("/upload")
    public String uploadForm() {
        return "/fileUpload/upload-form";
    }

//    @PostMapping("/upload")
    public String upload(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request);
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {

            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath = {}", fullPath);
                part.write(fullPath);
            }
        }
        return "redirect:/file/upload";
    }

//    @PostMapping("/upload")
    public String uploadWithSpring(@RequestParam(name = "musicFile") MultipartFile file) throws IOException {
        log.info("file = {}", file);
        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 fullPath = {}", fullPath);
            file.transferTo(new File((fullPath)));
        }
        return "redirect:/file/upload";
    }

    @PostMapping("/upload")
    public String realUpload(@RequestParam MultipartFile musicFile, @RequestParam List<MultipartFile> musicAlbum, HttpServletRequest request) {
        MusicDto dto = new MusicDto(musicFile, musicAlbum);
        String userId = (String) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

        musicService.save(dto, userId);
        return "redirect:/file/upload";
    }
}
