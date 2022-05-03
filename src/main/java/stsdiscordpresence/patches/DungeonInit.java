package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import stsdiscordpresence.Client;
import java.util.ArrayList;
import java.time.Instant;

@SpirePatch(
    clz=AbstractDungeon.class,
    method=SpirePatch.CONSTRUCTOR,
    paramtypez={
        String.class,
        String.class,
        AbstractPlayer.class,
        ArrayList.class,
    }
)
public class DungeonInit {
    @SpirePostfixPatch
    public static void Postfix() {
        Client.startTime = Instant.now().getEpochSecond();
        Client.FloorInfo();
    }
}