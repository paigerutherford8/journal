package client;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        String directory = "~/journal";
        String editor = "nano";
        LocalDateTime dateTime = LocalDateTime.now();
        ProcessUtils processUtils = ProcessUtils.getInstance();

        Runner.createInstance(directory, editor, dateTime, processUtils).run();
    }
}
