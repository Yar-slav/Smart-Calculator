import static org.junit.jupiter.api.Assertions.*;

import com.ginsberg.junit.exit.ExpectSystemExit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    @ExpectSystemExit
    void main() {
        String input = "/exit";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

//        assertEquals(BigInteger.valueOf(88), lineActual);
        Main.main(null);
    }
}