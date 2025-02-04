package soulchasm.main.Objects.Jars;

import necesse.entity.objectEntity.InventoryObjectEntity;
import necesse.level.maps.Level;

public class jarobjectentity extends InventoryObjectEntity {
    public jarobjectentity(Level level, int x, int y) {
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