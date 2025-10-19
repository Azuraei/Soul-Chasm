package soulchasm.main.incursion;

import necesse.engine.AbstractMusicList;
import necesse.engine.MusicList;
import necesse.engine.registries.MusicRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.MobSpawnTable;

public class SoulChasmBiome extends Biome {
    public static MobSpawnTable critters;
    public static MobSpawnTable mobs;

    public SoulChasmBiome() {
    }
    public AbstractMusicList getLevelMusic(Level level, PlayerMob perspective) {
        return new MusicList(MusicRegistry.VoidsEmbrace);
    }
    public MobSpawnTable getCritterSpawnTable(Level level) {
        return critters;
    }
    public MobSpawnTable getMobSpawnTable(Level level) {
        return mobs;
    }

    static {
        critters = (new MobSpawnTable()).add(25, "chasmcaveling").add(100, "wisp");
        mobs = (new MobSpawnTable()).add(80, "lostsoul").add(60, "chasmmage").add(10, "chasmmagestatue").add(10, "chasmwarriorstatue");
    }
}
