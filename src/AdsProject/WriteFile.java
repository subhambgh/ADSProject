package AdsProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {
    public static int priority = 1;
    public static BufferedWriter out = null;

    public static int getPriority() {return priority;}
    public static void setPriority(int priority) { WriteFile.priority = priority; }
    public static BufferedWriter getOut() {
        return out;
    }
    public static void setOut(BufferedWriter out) {
        WriteFile.out = out;
    }

    public static void writeLineWithNewLine(String output,int p) {
        if(p>=priority){
            try {
                out.write(output);
                out.newLine();
            }
            catch (IOException e) {
            }
        }
    }

    public static void writeLine(String output,int p) {
        if(p>=priority){
            try {
                out.write(output);
            }
            catch (IOException e) {
            }
        }

    }
    public static void close() throws IOException {
        out.close();
    }

}
