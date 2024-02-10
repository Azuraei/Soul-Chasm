package soulchasm.main.Objects.BiomeEnviroment;

import necesse.level.gameObject.TreeObject;

import static soulchasm.SoulChasm.SoulWoodColor;

public class soultree extends TreeObject {
    public soultree() {
        super("soultree", "soulwoodlogitem", "soultreesappling", SoulWoodColor, 60,80,100, "soultreeleaves");
    }
}
