package patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import java.util.ArrayList;



@SpirePatch(
    clz=CardLibrary.class,
    method="getCurse",
    paramtypez={}
)
public class RestrictCurses {
    public static String vanillaCurses[] = { "Clumsy", "Decay", "Doubt", "Injury", "Normality", "Pain", "Parasite", "Regret", "Shame", "Writhe" };
        
    @SpireInsertPatch(
        rloc=8,
        localvars={"tmp"}
    )
    public static void Insert(ArrayList<String> tmp) {
        ArrayList<String> cardsToRemove = new ArrayList<>();

        for (int i = 0; i < tmp.size(); i++) {
            String c = tmp.get(i);
            boolean available = false;

            for (String curse : vanillaCurses) {
                if (c == curse)
                    available = true;
            }

            if (!available)
                cardsToRemove.add(c);
        }

        for (int i = 0; i < cardsToRemove.size(); i++) {
            tmp.remove(cardsToRemove.get(i));
        }
    }
}