package com.atguigu.springcloud.algor;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: hexingquan
 * @Date: 2020/12/14 4:26 下午
 */
@Data
@Accessors(chain = true)
class TreeNode {
    int val;
    TreeNode left, right;
}
