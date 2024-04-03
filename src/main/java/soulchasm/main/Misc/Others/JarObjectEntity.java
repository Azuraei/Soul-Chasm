package soulchasm.main.Misc.Others;

import necesse.entity.objectEntity.InventoryObjectEntity;
import necesse.level.maps.Level;

public class JarObjectEntity extends InventoryObjectEntity {
    public JarObjectEntity(Level level, int x, int y) {
        super(level, x, y, 1);
    }

    public boolean canSetInventoryName() {return true;}

    public boolean canQuickStackInventory() {
        return true;
    }

    public boolean canRestockInventory() {
        return true;
    }

    public boolean canSortInventory() {
        return false;
    }

    public boolean canUseForNearbyCrafting() {
        return false;
    }
}