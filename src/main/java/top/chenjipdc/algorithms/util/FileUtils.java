package top.chenjipdc.algorithms.util;

import java.io.*;
import java.util.function.Consumer;

/**
 * @author chenjipdc@gmail.com
 * @date 2020-05-10 16:44
 */
public class FileUtils {

    /**
     * 随机生产数字到文件里面
     *
     * @param max 最多生产多少个数字，每个数字一行
     * @param path
     * @return
     */
    public static File random(int max, String path) {
        return random(max,
                path,
                null);
    }

    /**
     * 随机生产数字到文件里面
     *
     * @param max
     * @param path
     * @param consumer
     * @return
     */
    public static File random(int max, String path, Consumer<int[]> consumer) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                boolean ignored = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int[] arr = Utils.random(max);
            if (consumer != null) {
                consumer.accept(arr);
            }
            StringBuilder builder = new StringBuilder();
            for (int i : arr) {
                builder.append(i)
                        .append("\n");
            }
            outputStream.write(builder.toString()
                    .getBytes());
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignore) {

                }
            }
        }
        return file;
    }

    public static void createNew(String file){
        try {
            new File(file).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String file){
        new File(file).delete();
    }

    /**
     * 合并文件
     *
     * @param from
     * @param to
     * @param append
     */
    public static void merge(String from, String to, boolean append){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(from);
            outputStream = new FileOutputStream(to, append);
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 按行读取文件内容，并回调
     *
     * @param filePath
     * @param consumer
     */
    public static void read(String filePath, Consumer<Integer> consumer) {
        FileInputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            do {
                str = bufferedReader.readLine();
                if (str != null && consumer != null) {
                    consumer.accept(Integer.parseInt(str));
                }
            } while (str != null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入文件
     * @param path
     * @param bytes
     * @param append
     */
    public static void write(String path, byte[] bytes, boolean append) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path,
                    append);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按行打印文件内容
     *
     * @param filePath
     * @param lineCount
     */
    public static void prettyPrint(String filePath, int lineCount) {
        final IntegerWrapper wrapper = new IntegerWrapper(0);
        read(filePath,
                num -> {
                    wrapper.increment();
                    if (wrapper.getI() % lineCount == 0) {
                        System.out.println(num + ",\t");
                    } else {
                        System.out.print(num + ",\t");
                    }
                });
        System.out.println("总共大小： " + wrapper.getI());
    }

    /**
     * 为lambda表达式包装
     */
    private static final class IntegerWrapper {
        private Integer i;

        public IntegerWrapper(Integer i) {
            this.i = i;
        }

        public Integer increment() {
            i++;
            return i;
        }

        public Integer decrement() {
            --i;
            return i;
        }

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }
    }
}
