package top.chenjipdc.algorithms.sorted;

import top.chenjipdc.algorithms.util.FileUtils;
import top.chenjipdc.algorithms.util.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjipdc@gmail.com
 * @date 2020-05-10 13:46
 * <p>
 * <p>
 * 假设有一个文件很大很大 1T 以上，每一行是一个数字，将这个文件里面的数字排好序
 *
 * 一、切割文件外部有序，内部无序实现方法。
 * 1、对每个文件进行排序
 * 2、按顺序合并文件即可
 */
public class Test_01_02_MergeSortBigFile1 {

    private static final String FILE_PATH = "/tmp/numbers.txt";
    private static final int MAX = 100;
    private static final int LINE = 5;
    private static final int FILE_COUNT = 5;

    static {
        // 模拟文件
        FileUtils.random(MAX,
                FILE_PATH,
                arr -> {
                    System.out.println("排序前：");
                    Utils.prettyPrint(arr,
                            LINE);
                    System.out.println("总共大小：" + arr.length);
                });
    }

    public static void main(String[] args) throws IOException {
        sorted(FILE_PATH);
        System.out.println("排序后：");
        FileUtils.prettyPrint(FILE_PATH,
                MAX / LINE);
    }


    /**
     * 对文件内容进行排序
     *
     * @param filePath
     * @throws IOException
     */
    private static void sorted(String filePath) throws IOException {
        final int fileCount = FILE_COUNT;
        split(fileCount,
                filePath);
        merge(fileCount,
                filePath);
    }

    /**
     * 将文件切割
     *
     * @param fileCount 切割成多少个文件
     * @param filePath  原文件路径
     */
    private static void split(int fileCount, String filePath) {
        final String dir = filePath.substring(0,
                filePath.lastIndexOf(File.separator) + 1);
        for (int i = 0; i < fileCount; i++) {
            FileUtils.delete(dir + i);
        }
        final int split = MAX / fileCount;
        final StringBuilder[] builders = new StringBuilder[fileCount];
        for (int i = 0; i < fileCount; i++) {
            builders[i] = new StringBuilder();
        }
        FileUtils.read(filePath,
                num -> {
                    int n = num / split;
                    String numStr = num + "\n";
                    StringBuilder builder = builders[n];
                    builder.append(numStr);
                    if (builder.length() > 2048) {
                        FileUtils.write(dir + n,
                                builder.toString()
                                        .getBytes(),
                                true);
                        builder.delete(0,
                                builder.length());
                    }
                });
        for (int i = 0; i < fileCount; i++) {
            StringBuilder builder = builders[i];
            if (builder.length() > 0) {
                FileUtils.write(dir + i,
                        builder.toString()
                                .getBytes(),
                        true);
            }
        }
        for (int i = 0; i < fileCount; i++) {
            sortedSubFile(dir + i);
        }
        FileUtils.delete(filePath);
    }

    /**
     * 合并已经切割好的文件
     *
     * @param fileCount
     * @param filePath
     * @throws IOException
     */
    private static void merge(int fileCount, String filePath) throws IOException {
        final String dir = filePath.substring(0,
                filePath.lastIndexOf(File.separator) + 1);
        FileUtils.createNew(filePath);

        for (int i = 0; i < fileCount; i++) {
            String subFilePath = dir + i;
            FileUtils.merge(subFilePath,
                    filePath,
                    true);
            FileUtils.delete(subFilePath);
        }
    }

    /**
     * 对切割好的文件进行排序
     *
     * @param subFilePath
     */
    private static void sortedSubFile(String subFilePath) {
        final List<Integer> arr = new ArrayList<>(32);
        FileUtils.read(subFilePath,
                arr::add);
        arr.sort((a, b) -> {
            if (a < b) {
                return -1;
            } else if (a.equals(b)) {
                return 0;
            } else {
                return 1;
            }
        });
        StringBuilder builder = new StringBuilder();
        for (Integer integer : arr) {
            builder.append(integer)
                    .append("\n");
        }
        FileUtils.write(subFilePath,
                builder.toString()
                        .getBytes(),
                false);
    }
}
