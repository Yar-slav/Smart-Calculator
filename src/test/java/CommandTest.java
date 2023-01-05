import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ginsberg.junit.exit.ExpectSystemExit;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    @ExpectSystemExit
    void validCommandExit() {
        Command command = new Command();
        command.validCommand("/exit");
    }

    @Test
    void validCommandHelp() {
        Command command = new Command();
        boolean result = command.validCommand("/help");
        assertTrue(result);
    }

    @Test
    void validCommandIncorrect() {
        Command command = new Command();
        boolean result = command.validCommand("/incorrect");
        assertTrue(result);
    }

    @Test
    void validCommandHelpWithoutSlash() {
        Command command = new Command();
        boolean result = command.validCommand("help");
        assertFalse(result);
    }
}