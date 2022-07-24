package ambos.retroworldgen;

import ambos.retroworldgen.noise.InfdevNoiseGeneratorOctaves;
import net.minecraft.shared.Minecraft;
import net.minecraft.src.*;

import java.util.Random;

public class ChunkProviderRetro2 extends ChunkProviderRetro {
    private final Random rand;
    private InfdevNoiseGeneratorOctaves field_912_k;
    private InfdevNoiseGeneratorOctaves field_911_l;
    private InfdevNoiseGeneratorOctaves field_910_m;
    private World worldObj;

    public ChunkProviderRetro2(World world, long l) {
        super(world, l);
        this.worldObj = world;
        this.rand = new Random(l);
        this.field_912_k = new InfdevNoiseGeneratorOctaves(this.rand, 16);
        this.field_911_l = new InfdevNoiseGeneratorOctaves(this.rand, 16);
        this.field_910_m = new InfdevNoiseGeneratorOctaves(this.rand, 8);
        this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
        this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
    }

    @Override
    public void generateTerrain(int chunkX, int chunkZ, short[] tiles, BiomeGenBase[] biomes, double[] temperatures) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                double[][] array = new double[33][4];
                int k = (chunkX << 2) + i;
                int n = (chunkZ << 2) + j;
                for (int l = 0; l < array.length; ++l) {
                    array[l][0] = this.calculateNoise(k, l, n);
                    array[l][1] = this.calculateNoise(k, l, n + 1);
                    array[l][2] = this.calculateNoise(k + 1, l, n);
                    array[l][3] = this.calculateNoise(k + 1, l, n + 1);
                }
                for (k = 0; k < 32; ++k) {
                    double n2 = array[k][0];
                    double n3 = array[k][1];
                    double n4 = array[k][2];
                    double n5 = array[k][3];
                    double n6 = array[k + 1][0];
                    double n7 = array[k + 1][1];
                    double n8 = array[k + 1][2];
                    double n9 = array[k + 1][3];
                    for (int n10 = 0; n10 < 4; ++n10) {
                        double n11 = n10 / 4.0;
                        double n12 = n2 + (n6 - n2) * n11;
                        double n13 = n3 + (n7 - n3) * n11;
                        double n14 = n4 + (n8 - n4) * n11;
                        double n15 = n5 + (n9 - n5) * n11;
                        for (int n16 = 0; n16 < 4; ++n16) {
                            double n17 = n16 / 4.0;
                            double n18 = n12 + (n14 - n12) * n17;
                            double n19 = n13 + (n15 - n13) * n17;
                            //int n20 = n16 + (i << 2) << 11 | (j << 2) << 7 | (k << 2) + n10; // original
                            int n20 = n16 + (i << 2) << Minecraft.WORLD_HEIGHT_BITS + 4 | (j << 2) << Minecraft.WORLD_HEIGHT_BITS | (k << 2) + n10; // modified
                            //int n20 = n16 + i * 4 << Minecraft.WORLD_HEIGHT_BITS + 4 | 0 + j * 4 << Minecraft.WORLD_HEIGHT_BITS | k * 8 + n10; // bta
                            char c = (char)Minecraft.WORLD_HEIGHT_BLOCKS;
                            for (int n21 = 0; n21 < 4; ++n21) {
//                                double var53 = temperatures[(i * 4 + n16) * 16 + j * 4 + n21];
                                double n22 = n18 + (n19 - n18) * (n21 / 4.0);
                                int n23 = 0;
                                if ((k << 2) + n10 < 64) {
//                                    if (var53 < 0.5D && (k << 2) + n10 >= 64 - 1) {
//                                        n23 = Tile.ICE.id;
//                                    } else {
                                        n23 = Block.fluidWaterStill.blockID;
//                                    }
                                }
                                if (n22 > 0.0) {
                                    n23 = Block.stone.blockID;
                                }
                                tiles[n20] = (short) n23;
                                n20 += c;
                            }
                        }
                    }
                }
            }
        }
    }

    private double calculateNoise(double double1, double double2, double double3) {
        double n;
        if ((n = double2 * 4.0 - 64.0) < 0.0) {
            n *= 3.0;
        }
        double n2;
        double n3;
        if ((n2 = field_910_m.generateNoise(double1 * 684.412 / 80.0, double2 * 684.412 / 400.0, double3 * 684.412 / 80.0) / 2.0) < -1.0) {
            if ((n3 = field_912_k.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else if (n2 > 1.0) {
            if ((n3 = field_911_l.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else {
            double n4 = field_912_k.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n;
            double n5 = field_911_l.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n;
            if (n4 < -10.0) {
                n4 = -10.0;
            }
            if (n4 > 10.0) {
                n4 = 10.0;
            }
            if (n5 < -10.0) {
                n5 = -10.0;
            }
            if (n5 > 10.0) {
                n5 = 10.0;
            }
            n3 = n4 + (n5 - n4) * ((n2 + 1.0) / 2.0);
        }
        return n3;
    }

    @Override
    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        this.rand.setSeed(this.worldObj.getRandomSeed());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)i * l1 + (long)j * l2 ^ this.worldObj.getRandomSeed());
        double d = 0.25;

        int l3;
        int i6;
        int l10;
        int k15;
        for(l3 = 0; l3 < 8; ++l3) {
            i6 = k + this.rand.nextInt(16) + 8;
            l10 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            k15 = l + this.rand.nextInt(16) + 8;
            (new WorldGenDungeon(Block.cobbleStone.blockID, Block.cobbleStoneMossy.blockID, (String)null)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 10; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenClay(32)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 20; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.dirt.blockID, 32, false)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 10; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.gravel.blockID, 32, false)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 20; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreCoalStone.blockID, 16, true)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 20; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(64);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreIronStone.blockID, 8, true)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 2; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(32);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreGoldStone.blockID, 8, true)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 8; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(16);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreRedstoneStone.blockID, 7, true)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        for(l3 = 0; l3 < 1; ++l3) {
            i6 = k + this.rand.nextInt(16);
            l10 = this.rand.nextInt(16);
            k15 = l + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreDiamondStone.blockID, 7, true)).generate(this.worldObj, this.rand, i6, l10, k15);
        }

        d = 0.5;
        l3 = (int)((this.mobSpawnerNoise.func_806_a((double)k * d, (double)l * d) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
        if (l3 < 0) {
            l3 = 0;
        }

        if (this.rand.nextInt(10) == 0) {
            ++l3;
        }

        // Only generates big oak trees.
        Object obj = new WorldGenTreeShapeFancy(Block.leavesOakRetro.blockID, Block.logOak.blockID);
//        if (this.rand.nextInt(60) == 0) {
//            obj = new WorldGenTreeShapeFancy(Block.leavesCherry.blockID, Block.logCherry.blockID);
//        }

        int l17;
        for(l10 = 0; l10 < l3 * 6; ++l10) { // Increases tree density.
            k15 = k + this.rand.nextInt(16) + 8;
            l17 = l + this.rand.nextInt(16) + 8;
            ((WorldGenerator)((WorldGenerator)obj)).func_517_a(1.0, 1.0, 1.0);
            ((WorldGenerator)((WorldGenerator)obj)).generate(this.worldObj, this.rand, k15, this.worldObj.getHeightValue(k15, l17), l17);
        }

        int j19;
        for(l10 = 0; l10 < 2; ++l10) {
            k15 = k + this.rand.nextInt(16) + 8;
            l17 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            j19 = l + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.flowerYellow.blockID)).generate(this.worldObj, this.rand, k15, l17, j19);
        }

        if (this.rand.nextInt(2) == 0) {
            l10 = k + this.rand.nextInt(16) + 8;
            k15 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            l17 = l + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.flowerRed.blockID)).generate(this.worldObj, this.rand, l10, k15, l17);
        }

        if (this.rand.nextInt(4) == 0) {
            l10 = k + this.rand.nextInt(16) + 8;
            k15 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            l17 = l + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.rand, l10, k15, l17);
        }

        if (this.rand.nextInt(8) == 0) {
            l10 = k + this.rand.nextInt(16) + 8;
            k15 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            l17 = l + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.rand, l10, k15, l17);
        }

        for(l10 = 0; l10 < 10; ++l10) {
            k15 = k + this.rand.nextInt(16) + 8;
            l17 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            j19 = l + this.rand.nextInt(16) + 8;
            (new WorldGenReed()).generate(this.worldObj, this.rand, k15, l17, j19);
        }

        for(l10 = 0; l10 < 1; ++l10) {
            k15 = k + this.rand.nextInt(16) + 8;
            l17 = this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS);
            j19 = l + this.rand.nextInt(16) + 8;
            (new WorldGenCactus()).generate(this.worldObj, this.rand, k15, l17, j19);
        }

        for(l10 = 0; l10 < 50; ++l10) {
            k15 = k + this.rand.nextInt(16) + 8;
            l17 = this.rand.nextInt(this.rand.nextInt(Minecraft.WORLD_HEIGHT_BLOCKS - 8) + 8);
            j19 = l + this.rand.nextInt(16) + 8;
            (new WorldGenLiquids(Block.fluidWaterStill.blockID)).generate(this.worldObj, this.rand, k15, l17, j19);
        }

        for(l10 = 0; l10 < 20; ++l10) {
            k15 = k + this.rand.nextInt(16) + 8;
            l17 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            j19 = l + this.rand.nextInt(16) + 8;
            (new WorldGenLiquids(Block.fluidLavaStill.blockID)).generate(this.worldObj, this.rand, k15, l17, j19);
        }

        for(l10 = k + 8 + 0; l10 < k + 8 + 16; ++l10) {
            for(k15 = l + 8 + 0; k15 < l + 8 + 16; ++k15) {
                this.worldObj.findTopSolidBlock(l10, k15);
            }
        }

        BlockSand.fallInstantly = false;
    }
}
