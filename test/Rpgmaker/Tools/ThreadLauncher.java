package Rpgmaker.Tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLauncher {
    static ExecutorService pool = null;

    public static void execute(Runnable runnable) {
        if (pool == null) {
            pool = new ThreadPoolExecutor(10, 20, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
        }
        pool.execute(runnable);
    }
}
