package pe.edu.pucp.ticketflow.Infrastructure;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor {

    private static final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public static void ejecutar(Runnable tarea) {
        executor.submit(tarea);
    }
}