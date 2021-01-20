package mapper;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: hexingquan
 * @Date: 2020/12/31 6:07 下午
 */
public class ForkJoinTaskExample extends RecursiveTask<Long> {
    public static final long threshold = 1000000;
    private long start;
    private long end;
    public ForkJoinTaskExample(long start, long end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        long sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            long middle = (start + end) / 2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start, middle);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行结束合并其结果
            long leftResult = leftTask.join();
            long rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }
}
