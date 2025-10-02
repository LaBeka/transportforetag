package carSystem.logic;

public class EngineCommand {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void run() {
        command.execute();
    }
}
