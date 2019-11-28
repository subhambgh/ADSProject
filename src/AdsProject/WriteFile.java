package AdsProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/*
    Used to write the required outputs to a file.
 */
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

    /*
        Writes to the output file with a new line.
     */
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

    /*
        Writes to the output file without a new line.
     */
    public static void writeLine(String output,int p) {
        if(p>=priority){
            try {
                out.write(output);
            }
            catch (IOException e) {
            }
        }

    }
    /*
        Close the file.
     */
    public static void close() throws IOException {
        out.close();
    }

}
