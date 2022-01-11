package toy.jinseokshop.web.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@ToString
public class ItemDto {

    private String itemName;

    private int price;
    private int stockQuantity;

    private String DType;

    private String optionA;
    private String optionB;

    private List<MultipartFile> files;

}
