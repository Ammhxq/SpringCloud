import cn.hutool.aop.proxy.CglibProxyFactory;
import cn.hutool.aop.proxy.ProxyFactory;
import com.atguigu.springcloud.domin.bo.DebugInvocationHandler;
import com.atguigu.springcloud.domin.bo.User;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.service.impl.PaymentServiceImpl;
import com.atguigu.springcloud.util.ListUtils;
import com.atguigu.springcloud.util.Page;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @Author: hexingquan
 * @Date: 2020/12/11 6:56 下午
 */
public class CommonTest {
    public static void main(String[] args) {
        //new Factory().createProduct().productMethod();
      /*  add(1, 2, (x, y) -> System.out.println(x + y));
        add("Hello!", "Cattle", (x, y) -> System.out.println(x + y));*/
        List<String> strings = Lists.newArrayList("hexingquan", "gaotongbin",
                "zhousdsv", "sfsfsf", "dsfsdf", "derterterewrwwe", "sdas", "sdvxvsdfsd", "fgdfsdfdsc");
        List<List<String>> part = part(strings, 50);
        part.forEach(System.out::println);
        List<List<String>> lists = partString(strings, 50);
        lists.forEach(System.out::println);
        System.out.println("dev branch");

    }

    public static List<List<String>> part(List<String> list, int size) {
        if (CollectionUtils.isEmpty(list) || size < 1) {
            return Lists.newArrayList();
        }
        int limit = 0;
        System.out.println("csd");
        List<List<String>> res = Lists.newArrayList();
        List<String> patRes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).length() > size) {
                continue;
            }
            if (i == list.size() - 1) {
                res.add(patRes);
                res.add(Lists.newArrayList(list.get(i)));
                continue;
            }
            if (limit + list.get(i + 1).length() <= size) {
                patRes.add(list.get(i));
                limit += list.get(i).length();
            } else {
                res.add(patRes);
                patRes = new ArrayList<>();
                patRes.add(list.get(i));
                limit = list.get(i).length();
            }
        }
        return res;
    }

    public static List<List<String>> partString(List<String> list, int size) {
        if (CollectionUtils.isEmpty(list) || size < 1) {
            return Lists.newArrayList();
        }

        List<List<String>> res = Lists.newArrayList();
        List<String> patRes = new ArrayList<>();
        int tempNumber = 0;
        for (int index = 0; index < list.size(); index++) {
            String oneStr = list.get(index);
            if (null == oneStr) {
                continue;
            }

            // 初始化第 n 组的数值
            if (tempNumber == 0) {
                patRes = new ArrayList<>();
                res.add(patRes);
            }

            tempNumber += oneStr.length();
            patRes.add(oneStr);

            // 判断长度
            if (tempNumber > size) {
                patRes.remove(oneStr);
                tempNumber = 0;
                index--;
            }
        }
        return res;
    }


    @Test
    public void sdf() {
        Stream<String> s = Stream.of("te st", "t1", "t2", "te eeee", "aaaa");
        s.flatMap(n -> Stream.of(n.split(" "))).forEach(System.out::println);
        Optional<String> ll = Optional.empty();
        ProxyFactory proxyFactory = CglibProxyFactory.create();
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println("dscsd");
    }

    @Test
    public void sddf() {
       /* try {
            Class<?> tagetClass = Class.forName("com.atguigu.springcloud.domin.bo.TargetObject");
            TargetObject targetObject = (TargetObject) tagetClass.newInstance();
            Method[] methods = tagetClass.getDeclaredMethods();
            Method publicMethod = tagetClass.getDeclaredMethod("publicMethod", String.class);
            publicMethod.invoke(targetObject, "JavaGuide");
            Field field = tagetClass.getDeclaredField("value");
            //为了对类中的参数进行修改我们取消安全检查
            field.setAccessible(true);
            field.set(targetObject, "python");
            Method privateMethod = tagetClass.getDeclaredMethod("privateMethod");
            //为了调用private方法我们取消安全检查
            privateMethod.setAccessible(true);
            privateMethod.invoke(targetObject);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }*/

        PaymentService paymentService = (PaymentService) Proxy.newProxyInstance(PaymentServiceImpl.class.getClassLoader(),
                PaymentServiceImpl.class.getInterfaces(), new DebugInvocationHandler(new PaymentServiceImpl()));

        paymentService.selectById(123L);

        /*AliSmsService aliSmsService = (AliSmsService) com.atguigu.springcloud.strategy.CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
        AliSmsService<String> stringAliSmsService = new AliSmsService<>();
        stringAliSmsService.withInitial(() -> "sdssd");
        System.out.println(stringAliSmsService.getRes());*/
    }

    @Test
    public void test132() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        final ScheduledFuture<?> scheduledFuture = executorService
                .scheduleAtFixedRate(() -> System.out.println("beep!"), 0, 5, TimeUnit.SECONDS);                              // 每隔10s蜂鸣一次

        executorService.schedule(() -> {
            scheduledFuture.cancel(true);
        }, 1, TimeUnit.MINUTES);
        Thread.sleep(70000);
    }


    @Test
    public void test142() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(100);
            System.out.println("t1=" + t);
            return t;
        }).thenCompose(param -> CompletableFuture.supplyAsync(() -> {
            int t = param * 2;
            System.out.println("t2=" + t);
            return t;
        }));
        System.out.println("thenCompose result : " + f.get());
        ConcurrentHashMap<Object, Object> o = new ConcurrentHashMap<>();
        System.out.println(o.size());

    }

    @Test
    public void test1342() throws ExecutionException, InterruptedException {
        List<Object> objects = ListUtils.pageGetAll(1, 100, (offset, limit) -> {
            return new ArrayList<>();
        });
        final String s = "sdfs";
        System.out.println(s);
        System.out.println("==");
        Page page = new Page();
        /*ListUtils.pageGetAll(page,(d)->{
            return new ArrayList<>();
        });*/
        System.out.println(ListUtils.plus(5, ListUtils::add));
        System.out.println(ListUtils.plus(5, ListUtils::mul));


    }

    @Test
    public void test3() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
        List<ThreadInfo> threadInfos1 = Lists.newArrayList(threadInfos);
        System.out.println(Runtime.getRuntime().availableProcessors());
        int i = "ssd".hashCode();
    }

    @Test
    public void test33() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        User user = new User();
      /*  Future<?> submit = executorService.submit(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    user.addAge();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ok");
        executorService.submit(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    user.addAge();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/
        //System.out.println(submit.get());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
        Thread.sleep(5000L);
        //System.out.println(user.getSum());
    }

    public static int testFunction(int i, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {

        return function1.andThen(function2).apply(i);
    }

    public int testFunlction(int i, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {

        return function1.compose(function2).apply(i);
    }

    static <T> void add(T a, T b, BiConsumer<T, T> c) {
        c.accept(a, b);
    }

}
