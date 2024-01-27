package ambos.retroworldgen.world.type;

import ambos.retroworldgen.world.ChunkGeneratorInfdev;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderSingleBiome;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.perlin.overworld.retro.ChunkGeneratorOverworldRetro;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManagerGeneric;

public class WorldTypeOverworldInfdev extends WorldTypeOverworld {
    public WorldTypeOverworldInfdev(String languageKey) {
        super(languageKey, Weather.overworldClear, new WindManagerGeneric(), SeasonConfig.builder().withSingleSeason(Seasons.NULL).build());
    }

    public int getMinY() {
        return 0;
    }

    public int getMaxY() {
        return 127;
    }
    public int getOceanY() {
        return 64;
    }

    public BiomeProvider createBiomeProvider(World world) {
        return new BiomeProviderSingleBiome(Biomes.OVERWORLD_RETRO, 1.0, 1.0, 1.0);
    }

    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorInfdev(world);
    }

    public float getCloudHeight() {
        return 108.0F;
    }
}
