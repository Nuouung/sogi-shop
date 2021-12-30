package toy.jinseokshop.domain.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FileTest {

//    @Autowired MockMultipartFile multipartFile;
//    @Autowired MockMultipartHttpServletRequest request;

    @Test
    void operatingTest() {

    }

    @Test
    void fileTest() {
        // 실패. 어떻게 multipartFile을 가져오는지 알 수 없다.
    }

}