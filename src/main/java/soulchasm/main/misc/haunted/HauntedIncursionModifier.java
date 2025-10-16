package soulchasm.main.misc.haunted;

import necesse.entity.levelEvent.LevelEvent;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.incursion.BiomeTrialIncursionData;
import necesse.level.maps.incursion.IncursionData;
import necesse.level.maps.incursion.UniqueIncursionModifier;

public class HauntedIncursionModifier extends UniqueIncursionModifier {

    public int getModifierTickets(IncursionData data) {
        return data instanceof BiomeTrialIncursionData ? 0 : super.getModifierTickets(data);
    }

    public void onIncursionLevelGenerated(IncursionLevel level, int modifierIndex) {
        HauntedModifierLevelEvent event = new HauntedModifierLevelEvent();
        level.entityManager.events.add(event);
        level.gndData.setInt("haunted" + modifierIndex, event.getUniqueID());
    }

    public void onIncursionLevelCompleted(IncursionLevel level, int modifierIndex) {
        int eventUniqueId = level.gndData.getInt("haunted" + modifierIndex);
        LevelEvent event = level.entityManager.events.get(eventUniqueId, false);
        if (event != null) {
            event.over();
        }

    }
}