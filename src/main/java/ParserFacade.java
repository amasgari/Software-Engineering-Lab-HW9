import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ParserFacade {
    public void startParse(String path) {
        Parser parser = new Parser();
        File fp = new File(path);
        try {
            Scanner scanner = new Scanner(fp);
            parser.startParse(scanner);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
