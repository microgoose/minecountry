package net.minecountry.world.api.structure.service;

import net.minecountry.world.api.structure.config.ChunkConfig;
import net.minecountry.world.api.structure.config.RegionConfig;
import net.minecountry.world.api.structure.model.Chunk;
import net.minecountry.world.api.structure.model.Region;

public class ChunkService {
    public static Chunk getAtLocal(Region region, int localX, int localY) {
        return region.getChunk(getIndex(localX, localY));
    }

    public static Chunk getAtGlobal(Region region, int globalX, int globalY) {
        int chunkX = Math.floorDiv(globalX, ChunkConfig.BLOCKS_SIDE);
        int chunkY = Math.floorDiv(globalY, ChunkConfig.BLOCKS_SIDE);
        int localX = Math.floorMod(chunkX, RegionConfig.CHUNK_SIDE);
        int localY = Math.floorMod(chunkY, RegionConfig.CHUNK_SIDE);
        return getAtLocal(region, localX, localY);
    }

    private static int getIndex(int localX, int localY) {
        return localX + localY * RegionConfig.CHUNK_SIDE;
    }
}
