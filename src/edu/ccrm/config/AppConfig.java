package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton to hold app-wide configurations such as data folder paths.
 */
public class AppConfig {
    private static AppConfig instance;

    private Path dataDir;
    private Path backupDir;

    private AppConfig() {
        // Default configs
        dataDir = Paths.get("data");
        backupDir = Paths.get("backup");
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    /**
     * Loads configuration from resource or environment if needed.
     * For now, just the defaults.
     */
    public void loadConfig() {
        // Placeholder for loading config from a file or environment
        System.out.println("AppConfig: Data directory set to " + dataDir.toAbsolutePath());
    }

    public Path getDataDir() { return dataDir; }
    public Path getBackupDir() { return backupDir; }
}
