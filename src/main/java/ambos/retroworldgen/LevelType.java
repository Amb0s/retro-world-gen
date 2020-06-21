package ambos.retroworldgen;

public enum LevelType {
    DEFAULT("Default"),
    SKYLANDS("Skylands"),
    ALPHA("Alpha 1.1.2_01"),
    INFDEV("Infdev 20100415"),
    INLAND("Indev (Inland)"),
    FLOATING_ISLANDS("Indev (Floating islands)");

    public static LevelType selected = DEFAULT;
    private final String name;

    LevelType(String name) {
        this.name = name;
    }

    public LevelType next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    @Override
    public String toString() {
        return this.name;
    }
}
