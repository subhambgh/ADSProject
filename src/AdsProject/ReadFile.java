package AdsProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadFile {
    public static Map<Integer,String> readFile(String fileName) {
        BufferedReader reader;
        Map<Integer,String> map =  new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                int eventDay = Integer.parseInt(line.substring(0, line.indexOf(":")).trim());
                map.put(eventDay,line.substring(line.indexOf(":")+1).trim());
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}

