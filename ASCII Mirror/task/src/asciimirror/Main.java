package asciimirror;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Input the file path:");
        String filePath = scanner.nextLine();
        try {
            printContentOfFile(filePath);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private static void printContentOfFile(String filePath) throws Exception {
        // Open file
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        // Read line by line
        List<String> fileContents = new ArrayList<>();
        int maxLength = 0;
        while ((line = bufferedReader.readLine()) != null) {
            fileContents.add(line);
            maxLength = Math.max(maxLength, line.length());
        }
        // Close file
        bufferedReader.close();
        // Print content (with mirror)
        final int finalMaxLength = maxLength;
        fileContents.stream()
                .map(s -> mirror(s, finalMaxLength))
                .forEach(System.out::println);
    }

    private static String mirror(String txt, int length) {
        String modifiedLine = txt + " ".repeat(length - txt.length());
        StringBuilder revertLine =new StringBuilder();
        List<Character> chars = List.of(
                '<', '[', '{', '(', '/',
                '>', ']', '}', ')', '\\');
        for (int i = 0; i < modifiedLine.length(); i++) {
            char c = modifiedLine.charAt(modifiedLine.length() - 1 - i);
            int idx = chars.indexOf(c);
            if (idx >= 0) {
                int revertIdx = (chars.size()/2 + idx) % chars.size();
                revertLine.append(chars.get(revertIdx));
            } else {
                revertLine.append(c);
            }
        }
        return modifiedLine + " | " + revertLine.toString();
    }

    private static String cowAscii() {
        return """
                            ^__^
                    _______/(oo)
                /\\/(       /(__)
                   | w----||   \s
                   ||     ||   \s""";
    }
}