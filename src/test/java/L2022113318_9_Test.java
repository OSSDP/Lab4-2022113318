
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 测试用例设计原则
 * 1. 等价类划分：
 * 将输入数据分成有效和无效的等价类，以减少测试用例的数量。例如，对于 n 人，可以将 n 分为 1，和大于 1 的不同情况
 * 2. 边界值分析：
 * 测试输入的边界值，如 n = 1 、 n = 2 以及最大值 n = 2000 。
 * 3. 特例测试：
 * 包括特定的组合情况，比如所有人相互之间都不喜欢、部分人不喜欢等。
 * 4. 组合测试：
 * 测试不同的组合情况，比如大规模的输入、复杂的关系等，以确保代码在不同情况下的稳定性
 */
public class L2022113318_9_Test {
    private final Solution9 solution = new Solution9();

    /**
     * 测试目的：测试基本功能，确保简单用例能够正确处理。
     * 用例1: n = 4, dislikes = [[1, 2], [1, 3], [2, 4]]
     * 预期输出: true
     * 解释: 可以分成两组：[1, 4] 和 [2, 3]
     * 用例2: n = 3, dislikes = [[1, 2], [1, 3], [2, 3]]
     * 预期输出: false
     * 解释: 无法将所有人分成两组
     * 用例3: n = 5, dislikes = [[1, 2], [2, 3], [3, 4], [4, 5], [1, 5]]
     * 预期输出: false
     * 解释: 存在冲突，无法分组
     */
    @Test
    public void testBasicFunctionality() {
        assertTrue(solution.possibleBipartition(4, new int[][]{{1, 2}, {1, 3}, {2, 4}})); // true
        assertFalse(solution.possibleBipartition(3, new int[][]{{1, 2}, {1, 3}, {2, 3}})); // false
        assertFalse(solution.possibleBipartition(5, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}})); // false
    }

    /**
     * 测试目的：测试边界条件，验证函数在极端情况下的表现。
     * 用例4: n = 1 , dislikes = []
     * 预期输出: true
     * 解释: 只有一个人，自然可以分组
     * 用例5: n = 2 , dislikes = [[1, 2]]
     * 预期输出: true
     * 解释: 可以分成两组：[1] 和 [2]
     */
    @Test
    public void testBoundaryConditions() {
        assertTrue(solution.possibleBipartition(1, new int[][]{})); // true
        assertTrue(solution.possibleBipartition(2, new int[][]{{1, 2}})); // true
    }

    /**
     * 测试目的：测试大规模输入，确保在复杂情况下程序的稳定性。
     * 用例6: n = 2000 , dislikes = []
     * 预期输出: true
     * 解释: 没有冲突，所有人都可以被分成两组
     * 用例7: n = 2000 , dislikes = [[1, 2], [3, 4], ..., [1999, 2000]]
     * 预期输出: true
     * 解释: 可以将每对不喜欢的人放在不同的组
     */
    @Test
    public void testLargeInputs() {
        assertTrue(solution.possibleBipartition(2000, new int[][]{})); // true
        assertTrue(solution.possibleBipartition(2000, generateDislikes(2000))); // true
    }

    private int[][] generateDislikes(int n) {
        int[][] dislikes = new int[n - 1][2];
        for (int i = 1; i < n; i++) {
            dislikes[i - 1][0] = i;
            dislikes[i - 1][1] = i + 1;
        }
        return dislikes;
    }

    /**
     * 测试目的：测试复杂的冲突情况，确保函数能正确识别不能分组的情况。
     * 用例8: n = 6 , dislikes = [[1, 2], [2, 4], [4, 1]]
     * 预期输出: false
     * 解释: 存在环状的冲突，无法分组
     * 用例9: n = 6 , dislikes = [[1, 2], [2, 3], [3, 4], [4, 5], [1, 6]]
     * 预期输出: true
     * 解释: 可以分成两组，例如：[1, 3, 5] 和 [2, 4, 6]
     */
    @Test
    public void testComplexConflict() {
        assertFalse(solution.possibleBipartition(6, new int[][]{{1, 2}, {2, 4},  {4, 1}})); // false
        assertTrue(solution.possibleBipartition(6, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 6}})); // true
    }



    /**
     * 测试目的：测试完全互相不喜欢的情况，确保能返回正确结果。
     * 用例10: n = 3 , dislikes = [[1, 2], [1, 3], [2, 3]]
     * 预期输出: false
     * 解释: 任意两个之间都无法在同一组
     */
    @Test
    public void testAllDislikeEachOther() {
        assertFalse(solution.possibleBipartition(3, new int[][]{{1, 2}, {1, 3}, {2, 3}})); // false
    }

    /**
     * 测试目的：测试具有环状关系的情况，确保能正确识别冲突。
     * 用例11: n = 5 , dislikes = [[1, 2], [2, 3]，, [3, 4]，, [4, 5]，, [5, 1]]
     * 预期输出: false
     * 解释: 首尾相连的环形不可能分为两组
     */
    @Test
    public void testCircularDislikes() {
        assertFalse(solution.possibleBipartition(5, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5},{1, 5}})); // false
    }

    /**
     * 测试目的：测试复杂组合，不同的冲突情况。
     * 用例12: n = 6 , dislikes = [[1, 2], [1, 3], [4, 5], [2, 4]]
     * 预期输出: true
     * 解释: 可以分成两组，例如：[1, 4] 和 [2, 3, 5]
     * 用例13: n = 6 , dislikes = [[1, 2],  [3, 4],  [5, 6]]
     * 预期输出: true
     * 解释: 可以分成两组，例如：[1, 3, 5] 和 [2, 4, 6]
     */
    @Test
    public void testComplexCombinations() {
        assertTrue(solution.possibleBipartition(6, new int[][]{{1, 2}, {1, 3}, {4, 5}, {2, 4}})); // true
        assertTrue(solution.possibleBipartition(6, new int[][]{{1, 2}, {3, 4}, {5, 6}})); // true
    }

    /**
     * 测试目的：测试人对自己不喜欢的情况。
     * 用例14: n = 2 , dislikes = [[1, 1]]
     * 预期输出: false
     * 解释: 自己不喜欢自己，放到哪里都是错
     */
    @Test
    public void testSelfDislike() {
        assertFalse(solution.possibleBipartition(2, new int[][]{{1, 1}})); // false
    }

    /**
     * 测试目的：测试无冲突的全连接图。
     * 用例15: n = 4 , dislikes = [[1, 2],  [3, 4],  [2, 3]]
     * 预期输出: true
     * 解释: 可以分成两组，例如：[1, 3] 和 [2, 4]
     */
    @Test
    public void testFullyConnectedWithoutConflict() {
        assertTrue(solution.possibleBipartition(4, new int[][]{{1, 2}, {3, 4}, {2, 3}})); // true
    }
}
