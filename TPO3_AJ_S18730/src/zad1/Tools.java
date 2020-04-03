/**
 *
 *  @author Adamczyk Jakub S18730
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Tools {
    public static Options createOptionsFromYaml(String fileName) throws IOException {
        Yaml yaml = new Yaml();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
        Map map = yaml.load(isr);
        return new Options(map.get("host").toString(), (int) map.get("port"), (boolean) map.get("concurMode"), (boolean) map.get("showSendRes"), (Map<String, List<String>>) map.get("clientsMap"));
    }
}
