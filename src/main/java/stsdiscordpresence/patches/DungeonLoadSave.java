package stsdiscordpresence.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import stsdiscordpresence.Client;
import java.time.Instant;

@SpirePatch(
    clz=AbstractDungeon.class,
    method=SpirePatch.CONSTRUCTOR,
    paramtypez={
        String.class,
        AbstractPlayer.class,
        SaveFile.class,
    }
)
public class DungeonLoadSave {
    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon d, String name, AbstractPlayer p, SaveFile saveFile) {
        Client.startTime = Instant.now().getEpochSecond() - saveFile.play_time;
        Client.FloorInfo();
    }
}