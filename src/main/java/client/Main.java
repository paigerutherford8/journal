package client;

import org.apache.commons.cli.ParseException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        String directory = System.getProperty("user.home") + "/journal";
        String editor = "nano";
        LocalDateTime dateTime = LocalDateTime.now();
        ProcessUtils processUtils = ProcessUtils.getInstance();

        try {
            Runner.createInstance(directory, editor, dateTime, processUtils).run(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
