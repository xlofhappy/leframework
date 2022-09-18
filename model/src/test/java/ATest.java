import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import com.qweather.leframework.model.po.LePo;

import java.io.IOException;

/**
 * @author xiaole
 * @date 2021-06-29 18:09:42
 */
public class ATest {

    @Test
    public void test() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(new LePo());
        System.out.println(s);

        LePo lePo = objectMapper.readValue("{\"id\":null,\"remark\":null,\"authorId\":null,\"authorTime\":null,\"modifyerId\":null,\"modifyTime\":null,\"status\":0,\"display\":false}", LePo.class);
        System.out.println(1);
    }
}
