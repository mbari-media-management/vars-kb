package org.mbari.kb.ui;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.mbari.kb.jpa.knowledgebase.Factories;
import org.mbari.kb.ui.annotation.AnnosaurusService;
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
    private static ToolBelt toolBelt;
    private static Config config;
    private static final Logger log = LoggerFactory.getLogger(Initializer.class);

    /**
     * First looks for the file `~/.vars/vars-kb.conf` and, if found,
     * loads that file and uses the default configs as fallbacks. Otherwise
     * used the usual `reference.conf`/`application.conf` combination for
     * typesafe's config library.
     * @return
     */
    public static Config getConfig() {
        if (config == null) {
            Config defaultConfig =  ConfigFactory.load();
            final Path p0 = getSettingsDirectory();
            final Path path = p0.resolve("vars-kb.conf");
            if (Files.exists(path)) {
                config = ConfigFactory.parseFile(path.toFile())
                        .withFallback(defaultConfig);
            }
            else {
                config = defaultConfig;
            }
        }
        return config;
    }

    public static ToolBelt getToolBelt() {
        if (toolBelt == null) {
            Factories factories = Factories.build();
            toolBelt = new ToolBelt(factories.getKnowledgebaseDAOFactory(),
                    factories.getKnowledgebaseFactory(),
                    factories.getMiscDAOFactory(),
                    factories.getMiscFactory(),
                    factories.getPersistenceCacheProvider(),
                    factories.getConceptCache(),
                    new AnnosaurusService());
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

}
