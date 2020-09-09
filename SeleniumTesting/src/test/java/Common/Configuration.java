package Common;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    private static final String LOCAL_CONFIG_FILE_NAME = "config.yml";
    private static Map<String,String> configMap;
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    public static Map<String,String> readConfiguration(){
        if(configMap ==null){
            try{
                Yaml yaml = new Yaml();
                configMap = (Map<String,String>) yaml.load(new FileInputStream(LOCAL_CONFIG_FILE_NAME));
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        }
        return configMap;
    }

    public static String getBrowser(){
            return getProp("browser");
    }

    public static String getProp(String propName){
        if(readConfiguration().get(propName) == null){
            LOGGER.log(Level.SEVERE, "Property not found in yaml file");
            return null;
        }else {
            return configMap.get(propName);
        }
    }

}
