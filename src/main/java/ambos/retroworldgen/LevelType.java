package ambos.retroworldgen;

public enum LevelType {
    ALPHA("Alpha 1.1.2_01"),
    INLAND("Indev (Inland)"),
    FLOATING_ISLANDS("Indev (Floating islands)"),
    INFDEV("Infdev 20100415"),
    SKYLANDS("Skylands"),
    DEFAULT("Default");

    public static LevelType selected = DEFAULT;
    private final String name;

    LevelType(String name) {
        this.name = name;
    }

    public LevelType next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
