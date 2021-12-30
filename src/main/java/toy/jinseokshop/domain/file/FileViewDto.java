package toy.jinseokshop.domain.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import toy.jinseokshop.domain.board.Board;
import toy.jinseokshop.domain.file.MultipleFiles;
import toy.jinseokshop.domain.file.UploadFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class FileViewDto {

    private UploadFile file;
    private Board board;
    private List<MultipleFiles> files = new ArrayList<>();

    public FileViewDto(UploadFile file, Board board) {
        this.file = file;
        this.board = board;
    }

    public FileViewDto(UploadFile file, Board board, List<MultipleFiles> files) {
        this.file = file;
        this.board = board;
        this.files = files;
    }
}
