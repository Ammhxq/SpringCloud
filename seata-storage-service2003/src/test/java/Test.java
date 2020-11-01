import com.atguigu.springcloud.StorageMain2003;
import com.atguigu.springcloud.dao.StorageDao;
import com.atguigu.springcloud.service.StorageService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageMain2003.class)
public class Test {

    @Resource
    private StorageService storageService;

    @org.junit.Test
    public void Test(){
        storageService.decrease(1L,100);
    }
}
