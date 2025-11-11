package algo.path_traversal;

import java.io.File;
import java.io.IOException;

public class UsingEncodedSlash {

    public static void main(String[] args) throws IOException {
        // Example of using encoded slashes in file paths
        File parentFolder = new File("../../parent_folder");
        parentFolder.mkdir(); 
        File file = new File(parentFolder, "file.txt");
        file.createNewFile();
        System.out.println("File created at: " + file.getAbsolutePath());

        File encodedPath = new File("..%u2215..%u2215parent_folder%u2215file.txt");
        System.out.println("Encoded path: " + encodedPath.getPath());
        System.out.println("File exists: " + encodedPath.exists());
    }
}
