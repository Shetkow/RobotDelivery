import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    private static final Object monitor = new Object();
    public static final int LENGTH_STREAM = 1000;

    public static void main(String[] args) {


        ExecutorService executors = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            int index = i;
            AtomicInteger count = new AtomicInteger();
            executors.execute(() -> {
                String way = generateRoute("RLRFR", 100);
                for (int j = 0; j < way.length(); j++) {
                    if (way.charAt(j) == 'R') {
                        count.getAndIncrement();

                    }
                }
                int countToInt = count.get();
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countToInt)) {
                        sizeToFreq.put(countToInt, sizeToFreq.get(countToInt) + 1);
                    } else {
                        sizeToFreq.put(countToInt, 1);
                    }
                }

            });
        }

        executors.shutdown();
        int maxValue = 0;
        Integer maxKey = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxValue) {
                maxValue = sizeToFreq.get(key);
                maxKey = key;
            }
        }
        System.out.println();
        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось " + maxValue + " раз)");
        sizeToFreq.remove(maxKey);

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}