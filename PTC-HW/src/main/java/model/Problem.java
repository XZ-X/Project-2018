package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * @author XiangzheXu
 * create-time: 2018/12/29
 */
public class Problem {
    private File tmFile;
    private File inputFile;
    private File resultFile;
    private File consoleFile;

    public void initTest(File testDir) throws IOException {
        openTestFiles(testDir);

        TMBuilder TMBuilder = new TMBuilder();
        TM tm = TMBuilder.parseInput(tmFile);


    }

    private void startTest(TM tm) {
        try {
            Scanner scanner = new Scanner(inputFile);
            PrintWriter console = new PrintWriter(consoleFile);
            PrintWriter result = new PrintWriter(resultFile);
            while (scanner.hasNext()) {
                String testcase = scanner.nextLine();
                try {
                    tm.reset(testcase);
                    console.println("Input: " + testcase);
                    console.println("==================== RUN ====================");
                } catch (InputErrorException e) {
                    inputError(console, testcase);
                    result.println("Error");
                    continue;
                }

                try {
                    tm.run(console);
                    result.println("True");
                } catch (HaltException e) {
                    result.println("False");
                }



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openTestFiles(File testDir) throws IOException {
        final String TM_FILE = "test.tm";
        final String RESULT_FILE = "result.txt";
        final String INPUT_FILE = "input.txt";
        final String CONSOLE_FILE = "console.txt";


        File[] tmFiles = testDir.listFiles(file -> file.getName().equals(TM_FILE));
        if (tmFiles == null || tmFiles.length == 0) {
            System.err.println("Cannot find the tm file");
            exit(-1);
        }
        tmFile = tmFiles[0];

        File[] inputFiles = testDir.listFiles(file -> file.getName().equals(INPUT_FILE));
        if (inputFiles == null || inputFiles.length == 0) {
            System.err.println("Cannot find the input file");
            exit(-1);
        }
        inputFile = inputFiles[0];


        String testDirCanonicalPath = testDir.getCanonicalPath();
        consoleFile = new File(testDirCanonicalPath + File.separator + CONSOLE_FILE);
        if (!consoleFile.exists()) {
            boolean newFile = consoleFile.createNewFile();
            if (!newFile) {
                throw new IOException("Fail to create console file");
            }
        }
        resultFile = new File(testDirCanonicalPath + File.separator + RESULT_FILE);
        if (!resultFile.exists()) {
            if (!resultFile.createNewFile()) {
                throw new IOException("Fail to create result file");
            }
        }
    }

    private void inputError(PrintWriter writer, String errorInput) {
        writer.println("Input: " + errorInput);
        writer.println("==================== ERR ====================");
        writer.println("The input \"" + errorInput + "\" is illegal");
        writer.println("==================== END ====================");
        writer.flush();
    }
}
