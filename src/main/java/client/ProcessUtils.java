package client;

import java.io.IOException;
import java.util.concurrent.Executors;

class ProcessUtils {

    private static ProcessUtils INSTANCE = new ProcessUtils();

    private ProcessUtils() {}

    static ProcessUtils getInstance() {
        return INSTANCE;
    }

    void executeProcess(String[] command) {
        Process process = getProcess(command);
        StreamConsumer streamConsumer =
                new StreamConsumer(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamConsumer);
        int exitCode = 0;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert exitCode == 0;
    }

    private Process getProcess(String[] command) {
        ProcessBuilder builder = new ProcessBuilder().command(command);
        Process process = null;
        try {
            process = builder.inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert process != null;
        return process;
    }
}
