package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import stsdiscordpresence.Client;

@SpirePatch(
    clz=EndTurnButton.class,
    method="enable"
)
public class TurnStart {
    @SpirePostfixPatch
    public static void Postfix() {
        Client.FloorInfo();
    }
}