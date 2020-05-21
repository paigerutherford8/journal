package client;

import java.io.File;
import java.time.LocalDate;

class LocalStorageImpl extends LocalStorage {

    static LocalStorageImpl getInstance(String directory) {
        if (!directory.endsWith("/")) {
            directory += "/";
        }
        File file = new File(directory);
        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("Could not create local storage directory");
            }
        }
        return new LocalStorageImpl(directory);
    }

    private LocalStorageImpl(String directory) {
        this.directory = directory;
    }

    @Override
    String getFileForEntry(LocalDate date) {
        return getFileNameForEntry(date);
    }

    @Override
    boolean hasEntry(LocalDate date) {
        File file = new File(getFileNameForEntry(date));
        return file.exists();
    }
}
