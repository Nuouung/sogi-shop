package toy.jinseokshop.domain.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
public class File {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILE_ID")
    private Long id;

    @Embedded
    private UploadFile file;

    @OneToMany(mappedBy = "fileId", cascade = CascadeType.ALL)
    private List<MultipleFiles> files = new ArrayList<>();

    protected File() {}

    // == 연관관계 메소드 == //
    public void addFiles(UploadFile file) {
        MultipleFiles multipleFiles = MultipleFiles.storeFiles(file);
        files.add(multipleFiles);
    }

    // == 생성 메소드 == //
    public static File storeFile(UploadFile file, List<UploadFile> files) {
        File newFile = new File();
        newFile.setFile(file);
        for (UploadFile eachFile : files) {
            newFile.addFiles(eachFile);
        }
        return newFile;
    }
}
