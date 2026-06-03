package capabilities;

import constants.PathConstants;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class CapabilitiesLoader {
    private static final String CAPABILITIES_FILE_PATH = PathConstants.CAPABILITIES_FILE_PATH;

    private Map<String, Map<String, Object>> capabilitiesMap;

    @SuppressWarnings("unchecked")
    private void loadCapabilities() {
        if (capabilitiesMap == null) { // Lazy loading the capabilities
            Yaml yaml = new Yaml();
            try (InputStream inputStream = new FileInputStream(CAPABILITIES_FILE_PATH)) {
                capabilitiesMap = yaml.load(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load capabilities YAML file.", e);
            }
        }
    }

    public Map<String, Object> getCapabilities(String platform) {
        loadCapabilities();
        Map<String, Object> selectedCapabilities = capabilitiesMap.get(platform.toLowerCase());
        if (selectedCapabilities == null) {
            throw new IllegalArgumentException("No capabilities found for platform: " + platform);
        }
        return selectedCapabilities;
    }
}
