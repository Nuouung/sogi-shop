package toy.jinseokshop.domain.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import toy.jinseokshop.domain.board.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILE_ID")
    private Long id;

    @Embedded
    private UploadFile file;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @OneToMany(mappedBy = "fileId", cascade = CascadeType.ALL)
    private List<MultipleFiles> files = new ArrayList<>();

    private String haveMultiple;

    // == 연관관계 메소드 == //
    public void addFiles(UploadFile file) {
        MultipleFiles multipleFiles = MultipleFiles.storeFiles(file);
        multipleFiles.setFileId(this);
        files.add(multipleFiles);
    }

    // == 생성 메소드 == //
    public static File storeFile(Board board, @Nullable UploadFile file, @Nullable List<UploadFile> files) {
        File newFile = new File();
        if (file != null) newFile.setFile(file);
        newFile.setBoard(board);
        if (files != null) {
            for (UploadFile eachFile : files) {
                newFile.addFiles(eachFile);
            }
        }

        String haveMultiple = (files == null || files.isEmpty()) ? "n" : "y";
        newFile.setHaveMultiple(haveMultiple);

        return newFile;
    }
}
