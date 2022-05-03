package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.ModList;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import stsdiscordpresence.Client;

@SpirePatch(
    clz=MainMenuScreen.class,
    method=SpirePatch.CONSTRUCTOR,
    paramtypez={
        boolean.class
    }
)
public class Menu {
    @SpirePostfixPatch
    public static void Postfix() {
        System.out.println(ModList.getAllModListNames());
        Client.UpdatePresence("Main menu", "", false);
    }
}