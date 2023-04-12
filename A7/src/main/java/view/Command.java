package view;

public abstract class Command {
    private final String key;
    private final String desc;

    public Command(String key, String description) {
        this.key = key;
        this.desc = description;
    }

    public abstract void execute();

    public String getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.desc;
    }
}
