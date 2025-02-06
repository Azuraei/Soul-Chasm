package soulchasm.main.Misc.Presets;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;
import soulchasm.SoulChasm;

public class SoulCaveBigShrinePreset extends Preset {
    public SoulCaveBigShrinePreset(GameRandom random) {
        super(9,13);
        String preset_script = "PRESET = {\n" + "\twidth = 9,\n" + "\theight = 13,\n" + "\ttileIDs = [81, soulcaverocktile, 82, soulcavefloortile, 83, soulcavebrickfloortile, 84, soulcavetiledfloortile],\n" + "\ttiles = [81, 81, 81, 81, 81, 81, 81, 81, 81, 81, 81, 83, 82, 83, 81, 81, 81, 81, 81, 82, 82, 83, 84, 83, 81, 82, 81, 81, 81, 82, 81, 84, 83, 82, 83, 81, 81, 81, 83, 83, 84, 81, 83, 83, 81, 81, 83, 84, 83, 84, 83, 84, 81, 81, 81, 82, 83, 83, 84, 83, 83, 81, 81, 81, 83, 84, 81, 84, 81, 84, 83, 81, 81, 83, 81, 81, 84, 81, 83, 83, 81, 82, 83, 84, 83, 84, 83, 84, 83, 82, 82, 83, 83, 83, 83, 83, 83, 83, 82, 81, 82, 82, 82, 82, 82, 82, 82, 81, 81, 81, 81, 82, 81, 81, 82, 81, 81],\n" + "\tobjectIDs = [0, air, 1010, soulmonumentobject, 1013, statueobject, 1014, magestatueobject, 984, soulcavedecorations, 985, soulbrickwall, 1004, soullantern, 990, soulbrickwindow],\n" + "\tobjects = [0, 985, 990, 985, 985, 0, 990, 985, 0, 985, 985, 0, 0, 0, 0, 0, 985, 985, 0, 0, 984, 0, 1014, 0, 1004, 0, 985, 985, 0, 0, 0, 0, 0, 0, 0, 0, 985, 0, 0, 0, 1010, 0, 0, 0, 0, 0, 0, 1013, 0, 0, 0, 1013, 0, 985, 985, 0, 0, 0, 0, 0, 0, 0, 985, 985, 0, 1013, 0, 0, 0, 1013, 0, 985, 985, 985, 0, 0, 0, 0, 0, 985, 985, 0, 985, 1013, 0, 0, 0, 1013, 985, 0, 0, 985, 990, 985, 0, 985, 990, 985, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],\n" + "\trotations = [1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 2, 2, 1, 2, 1, 2, 2, 2, 1, 0, 2, 3, 2, 2, 2, 2, 2, 2, 2, 1, 3, 2, 2, 0, 2, 0, 2, 2, 1, 1, 2, 1, 0, 0, 0, 3, 0, 3, 2, 2, 3, 0, 0, 0, 3, 3, 2, 1, 1, 1, 0, 0, 0, 3, 1, 2, 1, 2, 2, 0, 0, 0, 0, 2, 1, 1, 2, 1, 0, 0, 0, 3, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, 2, 2, 2, 2, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0],\n" + "\ttileObjectsClear = true,\n" + "\twallDecorObjectsClear = true,\n" + "\ttableDecorObjectsClear = true,\n" + "\tobjectEntities = {\n" + "\t\tentity = {\n" + "\t\t\ttileX = 4,\n" + "\t\t\ttileY = 4,\n" + "\t\t\tdata = {\n" + "\t\t\t\tname = ,\n" + "\t\t\t\tINVENTORY = {\n" + "\t\t\t\t\tsize = 1\n" + "\t\t\t\t},\n" + "\t\t\t\tinteractedWith = true\n" + "\t\t\t}\n" + "\t\t}\n" + "\t}\n" + "}";
        this.applyScript(preset_script);
        this.addInventory(SoulChasm.chasmShrineLootTable, random, 4, 4);
    }
}