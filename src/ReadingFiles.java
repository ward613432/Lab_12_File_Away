//Allows us to use the FileChooser wizard GUI to pick files
import javax.swing.*;
//Needed imports for working w/ IO (input/output)
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;

public class  ReadingFiles {
    //need to add the "throws IOException" after typical main phrase
    public static void main(String[] args) throws IOException   {

        //Creates a JFileChooser object that will open the JFileChooser Wizard GUI
        //Allows user to search for files that they want read by the program
        //Much easier for the user than typing in a file directory manually
        JFileChooser chooser = new JFileChooser();

        //The try block will prompt the user to open a file
        //If an error occurs in this block, the catch block will handle the IO Exception
        try {
            //This variable will hold the users current working directory (program folder)
            //"user.dir" is shorthand for current working directory (project folder)
            File workingDirectory = new File(System.getProperty("user.dir"));

            //This will make the JFileChooser GUI default to look in the workingDirectory first
            //User can still navigate out of this folder if desired
            chooser.setCurrentDirectory(workingDirectory);

            //Checks to see if the user picks a file in the file chooser wizard
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                //Stores user selected file
                File selectedFile = chooser.getSelectedFile();
                //Holds the path to the selected file
                Path file = selectedFile.toPath();

                //InputStream is needed in order to create our Buffered Reader
                //InputStream allows bytes of data to be read from a file
                //BufferedReader is our actual "reader" tool that will be used to read characters from file
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));


                //Starts at line 0 and moves line by line through the file
                int line = 0;
                int spaces = 0;
                int chars = 0;
                int words = 0;
                //Rec holds what the reader finds on the line
                String rec = "";
                //Prints File Path of selected File
                System.out.println("File Path: " + file);
                //Moving through file, reading, and printing each line of the selected file
                while (reader.ready()) {
                    rec = reader.readLine();
                    line++;
                    words += rec.split(" ").length;
                    for (char c : rec.toCharArray()) {
                        if (c == ' ') {
                            spaces++;
                        } else {
                            chars++;
                        }
                    }

                    //Prints the line # and the contents of the line
                    System.out.printf("\nLine%4d: %-60s ", line, rec);
                }
                reader.close(); // must close the file to seal it and clear buffer
                System.out.println("\n\nData file read!"); //Success message

                System.out.println("File Summary");
                System.out.printf("Number of Lines: %d\n", line);
                System.out.printf("Number of Spaces: %d\n", spaces);
                System.out.printf("Number of Words: %d\n", words);
                System.out.printf("Number of Characters: %d\n", chars);
            } else {
                //This else statement is hit when the user closes the JFileChooser Wizard without selecting file
                System.out.println("File not selected. Please restart program.");
                System.exit(0); //Shuts down program
            }
        }
        //This catch block is hit when the user file the user attempts to open a file that can not be found
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
            //Prints the error along with additional info related to the error
            e.printStackTrace();
        }
        //This catch block is hit for all other IO Exceptions
        catch (IOException e) {
            //Prints the error along with additional info related to the error
            e.printStackTrace();
        }
    }
}
