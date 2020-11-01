package mapper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.atguigu.springcloud.PaymentMain8001;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = PaymentMain8001.class)
@RunWith(SpringRunner.class)
@Slf4j
public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static String toJSONString(Object object){

        return JSONObject.toJSONString(object, SerializerFeature.PrettyFormat);
    }

}