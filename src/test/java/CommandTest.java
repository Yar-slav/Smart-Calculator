import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ginsberg.junit.exit.ExpectSystemExit;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    @ExpectSystemExit
    void validCommand_ExitProgram_True() {
        Command command = new Command();
        command.validCommand("/exit");
    }

    @Test
    void validCommand_CallHelp_True() {
        Command command = new Command();
        boolean result = command.validCommand("/help");
        assertTrue(result);
    }

    @Test
    void validCommand_IncorrectRequest_True() {
        Command command = new Command();
        boolean result = command.validCommand("/incorrect request");
        assertTrue(result);
    }

    @Test
    void validCommand_HelpWithoutSlash_False() {
        Command command = new Command();
        boolean result = command.validCommand("help");
        assertFalse(result);
    }
}