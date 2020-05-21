package client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

abstract class LocalStorage {

    String directory;
    final private DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    String getFileNameForEntry(LocalDate date)  {
        return directory + fileFormat.format(date) + ".txt";
    }

    abstract String getFileForEntry(LocalDate date);

    abstract boolean hasEntry(LocalDate localDate);
}
