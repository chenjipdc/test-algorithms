package top.chenjipdc.algorithms.sorted;

import top.chenjipdc.algorithms.util.Utils;


/**
 * @author chenjipdc@gmail.com
 * @date 2020-05-10 13:46
 * <p>
 * 归并排序算法
 */
public class Test_01_01_MergeSort {

    private static int[] arr = Utils.random(100);
    private static final int LINE = 3;

    public static void main(String[] args) {
        System.out.println("原始数据：");
        Utils.prettyPrint(arr,
                LINE);

        System.out.println("排序后的数据：");
        sorted(arr);
        Utils.prettyPrint(arr,
                LINE);
    }

    public static void sorted(int[] arr) {
        if (arr.length > 0) {
            splitAndMerge(arr,
                    0,
                    arr.length - 1);
        }
    }

    /**
     * 分治，将数据不断切成两块，然后合并
     *
     * @param arr
     * @param left
     * @param right
     */
    private static void splitAndMerge(int[] arr, int left, int right) {
        // 切分到最小块就不需要再切分了
        if (right == left) {
            return;
        } else {
            int mid = (left + right) / 2;
            // 切分左边
            splitAndMerge(arr,
                    left,
                    mid);
            // 切分右边
            splitAndMerge(arr,
                    mid + 1,
                    right);

            // 将左右两边合并
            //            merge(arr, left, mid + 1, right);

            merge1(arr,
                    left,
                    mid + 1,
                    right,
                    arr.clone());
        }
    }

    /**
     * 方法1：内部创建临时数组用于数据取值比较
     *
     * @param arr   原数组
     * @param left
     * @param mid
     * @param right
     */
    private static void merge(int[] arr, int left, int mid, int right) {

        int leftMax = mid - left;
        int rightMax = right - mid + 1;

        // 创建两个临时数组记录左右两边数据，因为比较的时候会直接更改原数组
        int[] leftArr = new int[leftMax];
        int[] rightArr = new int[rightMax];

        for (int i = left; i < mid; i++) {
            leftArr[i - left] = arr[i];
        }

        for (int i = mid; i <= right; i++) {
            rightArr[i - mid] = arr[i];
        }


        int l = 0, r = 0;
        // 已排好序的position
        int pos = left;

        // 分别对比左右两边数据
        while (l < leftMax && r < rightMax) {
            // 左边的数小于右边，取左边的数
            if (leftArr[l] < rightArr[r]) {
                arr[pos] = leftArr[l];
                l++;
            } else {
                // 右边的数小于或等于左边，取右边的数
                arr[pos] = rightArr[r];
                r++;
            }
            pos++;
        }

        // 左边部分没比较完，则将左边未完成部分放到已排序好的数组里
        while (l < leftMax) {
            arr[pos] = leftArr[l];
            pos++;
            l++;
        }

        // 右边边部分没比较完，则将右边未完成部分放到已排序好的数组里
        while (r < rightMax) {
            arr[pos] = rightArr[r];
            pos++;
            r++;
        }

    }

    /**
     * 方法2：由外部传递进来之前需要排序的arr数组的完整复制tmp，用于取值比较
     *
     * @param arr   原数组
     * @param left
     * @param mid
     * @param right
     * @param tmp   arr的clone数组
     */
    private static void merge1(int[] arr, int left, int mid, int right, int[] tmp) {

        int leftMax = mid - left;
        int rightMax = right - mid + 1;

        int l = 0, r = 0;
        // 已排好序的position
        int pos = left;

        // 分别对比左右两边数据
        while (l < leftMax && r < rightMax) {
            // 左边
            int a = tmp[l + left];
            // 右边
            int b = tmp[r + mid];
            // 左边的数小于右边，取左边的数
            if (a < b) {
                arr[pos] = a;
                l++;
            } else {
                // 右边的数小于或等于左边，取右边的数
                arr[pos] = b;
                r++;
            }
            pos++;
        }

        // 左边部分没比较完，则将左边未完成部分放到已排序好的数组里
        while (l < leftMax) {
            arr[pos] = tmp[l + left];
            pos++;
            l++;
        }

        // 右边边部分没比较完，则将右边未完成部分放到已排序好的数组里
        while (r < rightMax) {
            arr[pos] = tmp[r + mid];
            pos++;
            r++;
        }

    }
}
