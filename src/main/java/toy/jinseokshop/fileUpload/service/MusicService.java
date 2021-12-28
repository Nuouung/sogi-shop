package toy.jinseokshop.fileUpload.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.fileUpload.controller.MusicDto;
import toy.jinseokshop.fileUpload.domain.Music;
import toy.jinseokshop.fileUpload.domain.UploadFile;
import toy.jinseokshop.fileUpload.repository.MusicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;

    public void save(MusicDto dto, String userId) {
        // 1. 개별
        UploadFile uploadFile = storeFile(dto.getAttachMusic());

        // 2. 복수
        List<UploadFile> uploadFiles = storeFiles(dto.getMusicAlbum());

        // 3. 멤버를 가져옴
        Member member = memberRepository.findByUserId(userId).orElseThrow();

        // 3. music entity 실어야 함
        Music music = Music.createMusic(member, uploadFile, uploadFiles);

        musicRepository.save(music);
    }

    private List<UploadFile> storeFiles(List<MultipartFile> musicAlbum) {
        List<UploadFile> storageFileNames = new ArrayList<>();
        for (MultipartFile multipartFile : musicAlbum) {
            if (!multipartFile.isEmpty()) {
                storageFileNames.add(storeFile(multipartFile));
            }
        }
        return storageFileNames;
    }

    private UploadFile storeFile(MultipartFile attachMusic) {
        if (attachMusic.isEmpty()) return null;

        String originalFilename = attachMusic.getOriginalFilename();
        String storageFileName = createStorageFileName(attachMusic); // uuid.xxx

        return new UploadFile(originalFilename, storageFileName);
    }

    private String createStorageFileName(MultipartFile attachMusic) {
        String ext = extractExt(attachMusic);
        String uuid = UUID.randomUUID().toString();

        return uuid + ext;
    }

    private String extractExt(MultipartFile musicFile) {
        String originalFilename = musicFile.getOriginalFilename();
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos);
    }
}
