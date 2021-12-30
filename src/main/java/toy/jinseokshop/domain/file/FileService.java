package toy.jinseokshop.domain.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void save(File file) {
        printFileConvertedResult(file);
        fileRepository.save(file);
    }

    public FileViewDto findByBoardId(Long boardId) {
        File foundFile = fileRepository.findByBoardId(boardId);
        printFileConvertedResult(foundFile);

        if (foundFile.getHaveMultiple().equals("y")) {
            foundFile = fileRepository.findByBoardIdWithJoin(boardId);
            return new FileViewDto(foundFile.getFile(), foundFile.getBoard(), foundFile.getFiles());
        } else {
            return new FileViewDto(foundFile.getFile(), foundFile.getBoard());
        }
    }

    private void printFileConvertedResult(File file) {
        log.info("=================== FILE +++++++++++++++++");
        if (file.getFile() != null) {
            log.info("file.getFile = [{}][{}]", file.getFile().getOriginalFileName(), file.getFile().getConvertedFileName());
        }

        List<MultipleFiles> files = file.getFiles();
        for (MultipleFiles multipleFiles : files) {
            log.info("multipleFiles = [{}][{}]", multipleFiles.getFile().getOriginalFileName(), multipleFiles.getFile().getConvertedFileName());
        }
        log.info("===================== FILE END ==================");
    }
}
