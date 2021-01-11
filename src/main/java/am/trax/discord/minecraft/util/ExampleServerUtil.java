package am.trax.discord.minecraft.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Utility class to get random example server addresses.
 * @author traxam
 */
@UtilityClass
public class ExampleServerUtil {
    private static final String SERVER_EXAMPLES_FILE_NAME = "/server-examples.txt";

    private static final Random random = new Random();
    private static List<String> exampleServerAddresses;

    /**
     * @return a random example server address.
     */
    public String getRandomExampleServerAddress() {
        if (exampleServerAddresses == null) {
            loadExampleServerAddresses();
        }

        return exampleServerAddresses.get(random.nextInt(exampleServerAddresses.size()));
    }

    private static void loadExampleServerAddresses() {
        Scanner scanner = new Scanner(ExampleServerUtil.class.getResourceAsStream(SERVER_EXAMPLES_FILE_NAME));
        exampleServerAddresses = new ArrayList<>();
        while (scanner.hasNextLine()) {
            exampleServerAddresses.add(scanner.nextLine());
        }
        scanner.close();
    }
}
