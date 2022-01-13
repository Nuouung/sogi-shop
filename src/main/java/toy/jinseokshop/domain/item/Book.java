package toy.jinseokshop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String publishingCompany;

    //==> 정적 팩토리 메소드
    public static Book createBook(String itemName, String content, int price, int stockQuantity, String author, String publishingCompany) {
        Book book = new Book();
        book.setItemName(itemName);
        book.setContent(content);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setPublishingCompany(publishingCompany);
        return book;
    }
}
