package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import stsdiscordpresence.Client;

@SpirePatch(
    clz=AbstractPlayer.class,
    method="loseGold"
)
public class BuyItem {
    @SpirePostfixPatch
    public static void Postfix() {
        if (AbstractDungeon.currMapNode.room instanceof ShopRoom)
            Client.FloorInfo();
    }
}