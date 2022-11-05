# Retro World Gen

## Addition
* Infdev 20100415 world gen
* Indev 20100223 world gen

## Installation

1. Install [MultiMC](https://multimc.org/) or [PolyMC](https://polymc.org/)
2. Download the BTA! Babric [instance](https://drive.google.com/file/d/1V6nHw_uErtckjTWjfbmX2_qebeTXLbQV/view)
3. Import the instance into your launcher
4. Download the "Retro World Gen" jar file
5. Copy it into your mods directory.

## Development

### Setup

1. Download or clone this repository
2. Follow the instructions for the [BTA Babric Minimal Mod](https://github.com/Turnip-Labs/bta-minimal-mod)

Important: if you want to use the `Minecraft Client` run configuration in your IDE, you need to import a JAR file.

1. Import the BTA! Babric instance into your launcher
2. Start the game (don't close it)
3. Go to Minecraft instance directory
4. Copy the JAR inside the `bin` directory to `libs`
5. Rename it to `minecraft-client-base.jar`

### Documentation

* [Fabric Wiki](https://fabricmc.net/wiki/doku.php)
* [Mixin javadoc](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/index.html)
* [Mixin cheatsheet](https://github.com/2xsaiko/mixin-cheatsheet/blob/master/README.md)
* [Cursed Legacy Wiki](https://minecraft-cursed-legacy.github.io/wiki/index.html)

Note: Since BTA is distributed without obfuscation, all Mixin classes must set the "remap" option to false.

### Example mods

* [BTA Babric Minimal Mod](https://github.com/Turnip-Labs/bta-minimal-mod)
* [BTA Babric Example Mod](https://github.com/Turnip-Labs/bta-example-mod)
* [Old BTA Example Mod for Babric](https://github.com/pkstDev/BTAExampleMod-babric)
* [Fabric Example Mod with StationAPI and BIN Mappings](https://github.com/calmilamsy/stationapi-example-mod/tree/dev/12)
* [Minecraft Cursed Legacy Example Mod](https://github.com/minecraft-cursed-legacy/Example-Mod)