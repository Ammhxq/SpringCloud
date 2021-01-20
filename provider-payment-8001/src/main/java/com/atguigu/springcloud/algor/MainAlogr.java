package com.atguigu.springcloud.algor;

import java.util.Objects;
import java.util.stream.LongStream;

/**
 * @Author: hexingquan
 * @Date: 2020/12/14 4:18 下午
 */
public class MainAlogr {

    static void traverse(ListNode head) {
        for (ListNode p = head; p != null; p = p.next) {
            System.out.println(head.val);
        }
    }

    static void treeTraverse(TreeNode root) {
        if (Objects.isNull(root)) {
            return;
        }
        System.out.println(root.val);
        treeTraverse(root.left);
        treeTraverse(root.right);
    }

    public static void main(String[] args) {
        Acc acc = new Acc();
        LongStream.rangeClosed(1,100000000L).parallel().forEach(acc::add);
        System.out.println(acc.total);
    }

    public static class Acc{
        private long total=0;
        public void add(long value){
            total +=value;
        }
    }

}
