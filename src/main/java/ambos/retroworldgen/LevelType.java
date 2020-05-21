package ambos.retroworldgen;

public enum LevelType {
    ALPHA("Alpha 1.1.2_01"),
    INDEV_INLAND("Indev (Inland)"),
    INFDEV("Infdev 20100415"),
    FLOATING_ISLANDS("Floating Islands"),
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
