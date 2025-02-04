package soulchasm.main.Misc.HauntedModifier;

import necesse.engine.registries.UniqueIncursionModifierRegistry;
import necesse.entity.levelEvent.LevelEvent;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.incursion.BiomeTrialIncursionData;
import necesse.level.maps.incursion.IncursionData;
import necesse.level.maps.incursion.UniqueIncursionModifier;

public class hauntedincursionmodifier extends UniqueIncursionModifier {
    public hauntedincursionmodifier(UniqueIncursionModifierRegistry.ModifierChallengeLevel challengeLevel) {
        super(challengeLevel);
    }

    public int getModifierTickets(IncursionData data) {
        return data instanceof BiomeTrialIncursionData ? 0 : super.getModifierTickets(data);
    }

    public void onIncursionLevelGenerated(IncursionLevel level, int modifierIndex) {
        hauntedmodifierlevelevent event = new hauntedmodifierlevelevent();
        level.entityManager.addLevelEvent(event);
        level.gndData.setInt("haunted" + modifierIndex, event.getUniqueID());
    }

    public void onIncursionLevelCompleted(IncursionLevel level, int modifierIndex) {
        int eventUniqueId = level.gndData.getInt("haunted" + modifierIndex);
        LevelEvent event = level.entityManager.getLevelEvent(eventUniqueId, false);
        if (event != null) {
            event.over();
        }

    }
}