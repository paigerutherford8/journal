package client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Runner {

    private String directory;
    private String editor;
    private LocalDateTime dateTime;
    private ProcessUtils processUtils;

    static Runner createInstance(String directory, String editor, LocalDateTime dateTime, ProcessUtils processUtils) {
        return new Runner(directory, editor, dateTime, processUtils);
    }

    private Runner(String directory, String editor, LocalDateTime dateTime, ProcessUtils processUtils) {
        this.directory = directory;
        this.editor = editor;
        this.dateTime = dateTime;
        this.processUtils = processUtils;
    }

    void run() {
        // Create journal directory if not already present
        String mkdir = "mkdir " + directory;
        processUtils.executeProcess(new String[] {"bash", "-c", mkdir});
        // Switch to alternate screen buffer
        String altBuff = "tput smcup";
        processUtils.executeProcess(new String[] {"bash", "-c", altBuff});
        // Open editor
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        processUtils.executeProcess(new String[] {editor, directory + dtf.format(dateTime) + ".txt"});
        // Return to normal screen buffer
        String normBuff = "tput rmcup";
        processUtils.executeProcess(new String[] {"bash", "-c", normBuff});
        // Exit gracefully
        System.exit(0);
    }
}
