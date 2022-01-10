package toy.jinseokshop.web.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.item.ItemRepository;
import toy.jinseokshop.domain.item.ItemService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemControllerTest {

    @Autowired ItemService itemService;

}