import model.Problem;

import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

/**
 * @author XiangzheXu
 * create-time: 2018/12/21
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please specify a directory!");
            exit(-1);
        }
        File testDir = new File(args[0]);
        if (!testDir.isDirectory()) {
            System.err.println(args[0] + " is not a directory");
            exit(-1);
        }

        Problem problem = new Problem();
        problem.initTest(testDir);

    }
}
