import java.util.*;

/**
 * @description:
 *
 * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
 *
 * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 4, dislikes = [[1,2],[1,3],[2,4]]
 * 输出：true
 * 解释：group1 [1,4], group2 [2,3]
 * 示例 2：
 *
 * 输入：n = 3, dislikes = [[1,2],[1,3],[2,3]]
 * 输出：false
 * 示例 3：
 *
 * 输入：n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * 输出：false
 *
 *
 * 提示：
 *
 * 1 <= n <= 2000
 * 0 <= dislikes.length <= 104
 * dislikes[i].length == 2
 * 1 <= dislikes[i][j] <= n
 * ai < bi
 * dislikes 中每一组都 不同
 *
 */
class Solution9 {

    public boolean possibleBipartition(int n, int[][] dislikes) {
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; ++i) {
            g[i] = new ArrayList<>();
        }

        // 构建邻接表
        for (int[] p : dislikes) {
            g[p[0]].add(p[1]);
            g[p[1]].add(p[0]);
        }

        // 颜色数组，0: 未染色, 1: 组1, -1: 组2
        int[] colors = new int[n + 1];

        // 遍历每个人
        for (int i = 1; i <= n; ++i) {
            if (colors[i] == 0) { // 还未访问
                if (!bfs(i, 1, g, colors)) { // 从未访问的节点开始 BFS
                    return false; // 如果发现冲突，返回 false
                }
            }
        }
        return true; // 所有人都可以被分成两组
    }

    private boolean bfs(int start, int color, List<Integer>[] g, int[] colors) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        colors[start] = color; // 将起始节点染为组1

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : g[current]) {
                if (colors[neighbor] == 0) { // 如果邻居未被染色
                    colors[neighbor] = -colors[current]; // 给邻居染上相反的颜色
                    queue.offer(neighbor);
                } else if (colors[neighbor] == colors[current]) {
                    return false; // 如果邻居与当前节点颜色相同，返回 false
                }
            }
        }

        return true; // BFS 完成，未发现冲突
    }

    public void unit(int x, int y, int[] fa) {
        x = findFa(x, fa);
        y = findFa(y, fa);
        if (x == y) {
            return;
        }
        if (fa[x] <= fa[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        fa[x] += fa[y];
        fa[y] = x;
    }

    public boolean isconnect(int x, int y, int[] fa) {
        x = findFa(x, fa);
        y = findFa(y, fa);
        return x == y;
    }

    public int findFa(int x, int[] fa) {
        return fa[x] < 0 ? x : (fa[x] = findFa(fa[x], fa));
    }
}
