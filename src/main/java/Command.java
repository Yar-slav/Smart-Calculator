public class Command {
    public boolean validCommand(String line) {
        if (line.startsWith("/")) {
            if (line.equals("/exit")) {
                System.out.println("Bye!");
                System.exit(0);
            } else if (line.equals("/help")) {
                help();
                return true;
            } else {
                System.out.println("Unknown command");
                return true;
            }
        }
        return false;
    }

    void help() {
        System.out.println("The program calculates of numbers");
        System.out.println("If you write even number of minuses '--' or '----' it will be '+'");
        System.out.println("If you write several plus sings like '++' or '+++' it will be '+'");
        System.out.println("If you write '-+' or '+-' it will be '-'");
    }
}
