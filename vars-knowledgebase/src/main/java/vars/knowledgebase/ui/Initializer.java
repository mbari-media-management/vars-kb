package vars.knowledgebase.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Brian Schlining
 * @since 2018-01-22T13:03:00
 */
public class Initializer {

    private static Path settingsDirectory;
    private static Injector injector;
    private static ToolBelt toolBelt;
    private static Config config;
    private static final Logger log = LoggerFactory.getLogger(Initializer.class);

    /**
     * First looks for the file `~/.vars/vars-annotation.conf` and, if found,
     * loads that file. Otherwise used the usual `reference.conf`/`application.conf`
     * combination for typesafe's config library.
     * @return
     */
    public static Config getConfig() {
        if (config == null) {
            final Path p0 = getSettingsDirectory();
            final Path path = p0.resolve("vars-kb.conf");
            if (Files.exists(path)) {
                config = ConfigFactory.parseFile(path.toFile());
            }
            else {
                config = ConfigFactory.load();
            }
        }
        return config;
    }

    public static ToolBelt getToolBelt() {
        if (toolBelt == null) {
            toolBelt = getInjector().getInstance(ToolBelt.class);
        }
        return toolBelt;
    }

    /**
     * The settingsDirectory is scratch space for VARS
     *
     * @return The path to the settings directory. null is returned if the
     *  directory doesn't exist (or can't be created) or is not writable.
     */
    public static Path getSettingsDirectory() {
        if (settingsDirectory == null) {
            String home = System.getProperty("user.home");
            Path path = Paths.get(home, ".vars");
            settingsDirectory = createDirectory(path);
            if (settingsDirectory == null) {
                log.warn("Failed to create settings directory at " + path);
            }
        }
        return settingsDirectory;
    }

    private static Path createDirectory(Path path) {
        Path createdPath = path;
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                if (!Files.isWritable(path)) {
                    createdPath = null;
                }
            }
            catch (IOException e) {
                String msg = "Unable to create a directory at " + path + ".";
                LoggerFactory.getLogger(Initializer.class).error(msg, e);
                createdPath = null;
            }
        }
        return createdPath;
    }

    public static Injector getInjector() {
        if (injector == null) {
            String moduleName = getConfig().getString("app.injector.module.class");
            try {
                Class clazz = Class.forName(moduleName);
                // TODO in java 9 use clazz.getDeclaredConstructor().newInstance()
                // You'll have to find one where constructor.getParameterCount == 0
                Module module = (Module) clazz.newInstance();
                injector = Guice.createInjector(module);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create dependency injector", e);
            }
        }
        return injector;
    }
}
