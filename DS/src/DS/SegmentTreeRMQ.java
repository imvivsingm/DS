package DS;

public class SegmentTreeRMQ {
    int[] tree;
    int n;

    public SegmentTreeRMQ(int[] arr) {
        n = arr.length;
        // size of segment tree array can be up to 4*n
        tree = new int[4 * n];
        build(arr, 0, n - 1, 0);
    }

    // Build the segment tree
    private void build(int[] arr, int start, int end, int node) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }
        int mid = (start + end) / 2;
        build(arr, start, mid, 2 * node + 1);
        build(arr, mid + 1, end, 2 * node + 2);
        tree[node] = Math.min(tree[2 * node + 1], tree[2 * node + 2]);
    }

    // Query minimum in range [l, r]
    public int query(int l, int r) {
        return queryUtil(0, n - 1, l, r, 0);
    }

    private int queryUtil(int start, int end, int l, int r, int node) {
        // completely outside
        if (r < start || l > end) {
            return Integer.MAX_VALUE;
        }
        // completely inside
        if (l <= start && end <= r) {
            return tree[node];
        }
        // partially inside
        int mid = (start + end) / 2;
        int leftMin = queryUtil(start, mid, l, r, 2 * node + 1);
        int rightMin = queryUtil(mid + 1, end, l, r, 2 * node + 2);
        return Math.min(leftMin, rightMin);
    }

    // Optional: update value at index idx to val
    public void update(int idx, int val) {
        updateUtil(0, n - 1, idx, val, 0);
    }

    private void updateUtil(int start, int end, int idx, int val, int node) {
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = (start + end) / 2;
        if (idx <= mid) {
            updateUtil(start, mid, idx, val, 2 * node + 1);
        } else {
            updateUtil(mid + 1, end, idx, val, 2 * node + 2);
        }
        tree[node] = Math.min(tree[2 * node + 1], tree[2 * node + 2]);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 7, 9, 11};
        SegmentTreeRMQ segTree = new SegmentTreeRMQ(arr);

        System.out.println("Min between 1 and 4: " + segTree.query(1, 4)); // output 2
        segTree.update(2, 0);
        System.out.println("Min between 1 and 4 after update: " + segTree.query(1, 4)); // output 0
    }
}

