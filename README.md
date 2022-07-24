# Retro World Gen

## Addition
* Infdev 20100415 world gen

## Installation

1. Install [MultiMC](https://multimc.org/) or [PolyMC](https://polymc.org/)
2. Install Babric [PolyMC/MultiMC instance](https://github.com/babric/polymc-instance)
3. Download the "Better than Adventure!" mod
4. Install it as a jar mod and make sure BTA is above Babric but below Minecraft
5. Select Fabric Loader and click "Edit"
6. Remove the whole "requires" block from the JSON, so intermediaries can be deleted
7. Save the JSON file and close it, then remove the intermediaries from the instance.
8. Download the "Retro World Gen" jar file
9. Copy it in your mods directory.

## Development

### Setup

1. Grab a "Better than Adventure!" jar from the PolyMC/MultiMC instance and rename it to "bta.jar".
2. Place the jar in the "libs" folder in your project.
3. Build the mod with `./gradlew build`

### Tips

* Since BTA is distributed without obfuscation, all Mixin classes must set the "remap" option to false.
* To decompile the whole "bta.jar" file, use [RetroMCP-Java](https://github.com/MCPHackers/RetroMCP-Java).

### Documentation
* [Fabric Wiki](https://fabricmc.net/wiki/doku.php)
* [Mixin javadoc](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/index.html)
* [Mixin cheatsheet](https://github.com/2xsaiko/mixin-cheatsheet/blob/master/README.md)
* [Cursed Legacy Wiki](https://minecraft-cursed-legacy.github.io/wiki/index.html)

### Example mods
* [BTA Example Mod for Babric](https://github.com/pkstDev/BTAExampleMod-babric)
* [Fabric Example Mod with StationAPI and BIN Mappings](https://github.com/calmilamsy/stationapi-example-mod/tree/dev/12)
* [Minecraft Cursed Legacy Example Mod](https://github.com/minecraft-cursed-legacy/Example-Mod)
