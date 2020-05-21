package client;

import org.apache.commons.cli.ParseException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        String directory = System.getProperty("user.home") + "/journal";
        LocalStorage localStorage = LocalStorageImpl.getInstance(directory);
        Terminal terminal = TerminalImpl.createInstanceWithEditorViewer("nano", "less");
        LocalDateTime dateTime = LocalDateTime.now();
        try {
            Runner.createInstance(localStorage, terminal, dateTime).run(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
