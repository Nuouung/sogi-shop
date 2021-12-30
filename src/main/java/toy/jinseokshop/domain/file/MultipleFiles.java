package toy.jinseokshop.domain.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PACKAGE)
public class MultipleFiles {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILES_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    private File fileId;

    @Embedded
    private UploadFile file;

    protected MultipleFiles() {}

    // == 생성 메소드 == //
    public static MultipleFiles storeFiles(UploadFile file) {
        MultipleFiles multipleFiles = new MultipleFiles();
        multipleFiles.setFile(file);
        return multipleFiles;
    }
}
