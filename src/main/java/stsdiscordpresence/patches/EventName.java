package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import stsdiscordpresence.Client;

@SpirePatch(
    clz=LocalizedStrings.class,
    method="getEventString"
)
public class EventName {
    @SpirePostfixPatch
    public static void Postfix() {
        Client.FloorInfo();
    }
}