package toy.jinseokshop.fileUpload.controller;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class MusicDto {

    private MultipartFile attachMusic;
    private List<MultipartFile> musicAlbum;

    public MusicDto(MultipartFile attachMusic, List<MultipartFile> musicAlbum) {
        this.attachMusic = attachMusic;
        this.musicAlbum = musicAlbum;
    }
}
