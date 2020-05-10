package top.chenjipdc.algorithms.util;

import java.util.Random;

/**
 * @author chenjipdc@gmail.com
 * @date 2020-05-10 15:38
 */
public class Utils {

    public static int randdomInt(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    public static int[] random(int max) {
        int[] arr = new int[max];
        Random random = new Random();
        for (int i = 0; i < max; i++) {
            arr[i] = random.nextInt(max);
        }
        return arr;
    }

    public static void prettyPrint(int[] arr, int line) {
        int l = arr.length;
        int c = l / line;
        for (int i = 0; i < l; i++) {
            if (i + 1 == l) {
                System.out.print(arr[i]);
            } else if ((i + 1) % c == 0) {
                System.out.println(arr[i] + ",\t");
            } else {
                System.out.print(arr[i] + ",\t");
            }
        }
        System.out.println();
    }

    public static void prettyPrint(int[] arr) {
        prettyPrint(arr,
                1);
    }
}
