import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Utils {

    public static byte[] hexStringParser(String hexString) {
        String byteCode = hexString.substring(hexString.indexOf("x") + 1, hexString.length());
        byte[] byteArray = new byte[byteCode.length() / 2];

        for(int i = 0, j = 0; i < byteCode.length() / 2; byteArray[i++] = (byte) Integer.parseInt(byteCode.substring(j++, ++j), 16));

        return byteArray;
    }

    public static String readFromFile(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(filename));
        StringBuilder fileContent = new StringBuilder();

        while (sc.hasNext()) {
            fileContent.append(sc.next());
        }

        return fileContent.toString();
    }

    public static String jsonSerializer(Map<String,String> keyValueMap) {
        StringBuffer jsonBuffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = keyValueMap.entrySet().iterator();

        jsonBuffer.append("[{");

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            jsonBuffer.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
            if(iterator.hasNext()) jsonBuffer.append(",");
        }

        jsonBuffer.append("}]");

        return jsonBuffer.toString();
    } 

    public static List<Map<String,String>> jsonParser(String json) {
        List<Map<String,String>> jsonContent = new ArrayList<Map<String,String>>();
        int index = 0;
        int leftKeyPointer;
        int rightKeyPointer;
        int leftValuePointer;
        int rightValuePointer;
        
        if (json.isEmpty()) return jsonContent;
        
        while (index >= 0 && json.charAt(index) != ']') {
            Map<String,String> keyValueMap = new HashMap<String,String>();
            
            index = json.indexOf('{', index);
            if(index != -1) {
                while(true) {
                    index = json.indexOf('"', index);
                    if(index == -1) break;
                    leftKeyPointer = index + 1;
                    rightKeyPointer = json.indexOf('"', ++index);
                    index = json.indexOf(':', index);

                    index = json.indexOf('"', index);
                    leftValuePointer = index + 1;
                    rightValuePointer = json.indexOf('"', ++index);
                    index = rightValuePointer;

                    keyValueMap.put(json.substring(leftKeyPointer,rightKeyPointer), json.substring(leftValuePointer,rightValuePointer));
                    index++;
                    if(json.charAt(index) == '}') {
                        break;
                    };
                }
                if(keyValueMap.size() != 0) jsonContent.add(keyValueMap);
            } else {

                break;
            }
            
        }

        return jsonContent;
    }
}
