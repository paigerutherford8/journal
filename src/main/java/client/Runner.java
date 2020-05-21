package client;

import org.apache.commons.cli.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

class Runner {

    private LocalStorage localStorage;
    private Terminal terminal;
    private LocalDateTime dateTime;

    static Runner createInstance(LocalStorage localStorage, Terminal terminal, LocalDateTime dateTime) {
        return new Runner(localStorage, terminal, dateTime);
    }

    private Runner(LocalStorage localStorage, Terminal terminal, LocalDateTime dateTime) {
        this.localStorage = localStorage;
        this.terminal = terminal;
        this.dateTime = dateTime;
    }

    void run(String[] args) throws ParseException {
        CommandLine cmd = parse(args);
        LocalDate currentDate = dateTime.toLocalDate();
        Optional<LocalDate> enteredDate = cmd.hasOption("d")
                ? Optional.of(stringToDate(cmd.getOptionValue("d")))
                : Optional.empty();
        if (enteredDate.isEmpty() || enteredDate.get().equals(currentDate)) {
            terminal.editFile(localStorage.getFileForEntry(currentDate));
        } else if (localStorage.hasEntry(enteredDate.get())) {
            terminal.viewFile(localStorage.getFileForEntry(enteredDate.get()));
        }
    }

    private CommandLine parse(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("d", "date", true, "date of entry to display/edit");
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    private LocalDate stringToDate(String s) {
        s += "/" + dateTime.getYear();
        DateTimeFormatter stringFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(s, stringFormat);
    }

}