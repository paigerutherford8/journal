package client;

import org.apache.commons.cli.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

class Runner {

    private String directory;
    private String editor;
    private LocalDateTime dateTime;
    private ProcessUtils processUtils;

    static Runner createInstance(String directory, String editor, LocalDateTime dateTime, ProcessUtils processUtils) {
        if (!directory.endsWith("/")) {
            directory += "/";
        }
        return new Runner(directory, editor, dateTime, processUtils);
    }

    private Runner(String directory, String editor, LocalDateTime dateTime, ProcessUtils processUtils) {
        this.directory = directory;
        this.editor = editor;
        this.dateTime = dateTime;
        this.processUtils = processUtils;
    }

    void run(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("d", "date", true, "date of entry to display/edit");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        // Create journal directory if not already present
        File file = new File(directory);
        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("Could not create application directory");
            }
        }

        // Switch to alternate screen buffer
        String altBuff = "tput smcup";
        processUtils.executeProcess(new String[] {"bash", "-c", altBuff});

        DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (cmd.hasOption("d") && stringToDate(cmd.getOptionValue("d")).equals(dateTime.toLocalDate())
                || !cmd.hasOption("d")) {
            // Open editor for current entry
            processUtils.executeProcess(new String[] {editor, directory + fileFormat.format(dateTime) + ".txt"});
        } else {
            // View entry for requested date
            processUtils.executeProcess(new String[] {"less", directory + fileFormat.format(dateTime) + ".txt"});
        }

        // Return to normal screen buffer
        String normBuff = "tput rmcup";
        processUtils.executeProcess(new String[] {"bash", "-c", normBuff});

        // Exit gracefully
        System.exit(0);
    }

    private LocalDate stringToDate(String s) {
        s += "/" + dateTime.getYear();
        DateTimeFormatter stringFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(s, stringFormat);
    }

}