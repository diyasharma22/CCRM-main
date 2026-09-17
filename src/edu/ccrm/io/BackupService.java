package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class BackupService {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path BACKUP_ROOT = Paths.get("backup");

    public void backupExports() throws IOException {
        // Ensure the source data directory exists
        if (!Files.exists(DATA_DIR) || !Files.isDirectory(DATA_DIR)) {
            throw new IOException("Source data directory not found. Nothing to back up.");
        }

        // Create the root backup directory if it doesn't exist
        if (!Files.exists(BACKUP_ROOT)) {
            Files.createDirectories(BACKUP_ROOT);
        }

        // Create a timestamped directory for this specific backup
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = BACKUP_ROOT.resolve("backup_" + timestamp);
        Files.createDirectory(backupDir);

        // Use a try-with-resources block to ensure the stream is closed
        try (Stream<Path> walk = Files.walk(DATA_DIR)) {
            walk.filter(Files::isRegularFile).forEach(sourceFile -> {
                try {
                    Path destinationFile = backupDir.resolve(DATA_DIR.relativize(sourceFile));
                    Files.createDirectories(destinationFile.getParent());
                    Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    // This is crucial for debugging: wrap the exception to provide context
                    throw new RuntimeException("Failed to back up file: " + sourceFile.toString(), e);
                }
            });
        }
        System.out.println("Backup completed successfully at: " + backupDir.toAbsolutePath());
    }
}