package client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String directory = "~/journal";
        String editor = "nano";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();

        // Create journal directory if not already present
        String mkdir = "mkdir " + directory;
        executeProcess(getProcess(new String[] {"bash", "-c", mkdir}));
        // Switch to alternate screen buffer
        String altBuff = "tput smcup";
        executeProcess(getProcess(new String[] {"bash", "-c", altBuff}));
        // Open editor
        executeProcess(getProcess(new String[] {editor, "~/journal/" + dtf.format(now) + ".txt"}));
        // Return to normal screen buffer
        String normBuff = "tput rmcup";
        executeProcess(getProcess(new String[] {"bash", "-c", normBuff}));
        // Exit gracefully
        System.exit(0);
    }

    private static Process getProcess(String[] command) {
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

    private static void executeProcess(Process process) {
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
}
