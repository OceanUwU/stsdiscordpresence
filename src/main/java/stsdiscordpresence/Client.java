package stsdiscordpresence;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.rewards.chests.MediumChest;
import com.megacrit.cardcrawl.rewards.chests.LargeChest;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordRPC;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static long startTime = -1L;

    public static ArrayList<String> portraits = new ArrayList<String>(Arrays.asList("ironclad", "silent", "defect", "watcher", "hermit", "slimeboss", "guardian", "hexaghost", "champ", "automaton", "gremlins", "snecko"));

    public static void UpdatePresence(String details, String state, String image, String smallImage, boolean showTime) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(state)
            .setDetails(details)
            .setBigImage(image, "Slay the Spire");
        if (showTime)
            builder.setStartTimestamps(showTime ? startTime : null);
        if (smallImage != "")
            builder.setSmallImage(smallImage, null);
        DiscordRichPresence rich = builder.build();
        DiscordRPC.discordUpdatePresence(rich);
        System.out.println("set discord rich presence");
    }

    public static void FloorInfo() {
        if (startTime == -1L)
            return;
        if (AbstractDungeon.currMapNode == null)
            return;

        boolean hasKeys = Settings.isFinalActAvailable && Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey;
        String roomType;
        String smallImage;

        if (AbstractDungeon.currMapNode.room instanceof NeowRoom) {
            roomType = "Neow";
            smallImage = "neow";
        } else if (AbstractDungeon.currMapNode.room instanceof MonsterRoom) {
            String enemyType;
            if (AbstractDungeon.currMapNode.room instanceof MonsterRoomBoss) {
                enemyType = "Boss";
                smallImage = "boss";
            } else if (AbstractDungeon.currMapNode.room instanceof MonsterRoomElite) {
                enemyType = "Elite";
                smallImage = "elite";
            } else {
                enemyType = "Enemy";
                smallImage = "enemy";
            }
            roomType = enemyType + " - " + Pattern.compile(".+:", Pattern.MULTILINE).matcher(AbstractDungeon.lastCombatMetricKey).replaceAll("") + " - Turn " + AbstractDungeon.actionManager.turn;
        }
        else if (AbstractDungeon.currMapNode.room instanceof RestRoom) {
            roomType = "Rest site";
            smallImage = "rest";
        } else if (AbstractDungeon.currMapNode.room instanceof ShopRoom) {
            roomType = "Merchant - " + AbstractDungeon.player.gold + "G";
            smallImage = "shop";
        } else if (AbstractDungeon.currMapNode.room instanceof TreasureRoom) {
            String chestSize;
            if (((TreasureRoom)AbstractDungeon.currMapNode.room).chest instanceof LargeChest)
                chestSize = "Large";
            else if (((TreasureRoom)AbstractDungeon.currMapNode.room).chest instanceof MediumChest)
                chestSize = "Medium";
            else
                chestSize = "Small";
            roomType = "Chest - " + chestSize;
            smallImage = "chest";
        }
        else if (AbstractDungeon.currMapNode.room instanceof TreasureRoomBoss) {
            roomType = "Boss chest";
            smallImage = "bosschest";
        }
        else if (AbstractDungeon.currMapNode.room instanceof VictoryRoom) {
            roomType = hasKeys ? "Act 4 door" : "Victory?";
            smallImage = "win";
        } else if (AbstractDungeon.currMapNode.room instanceof TrueVictoryRoom) {
            roomType = "Victory!";
            smallImage = "win";
        } else if (AbstractDungeon.currMapNode.room instanceof EventRoom) {
            AbstractEvent event = AbstractDungeon.currMapNode.room.event;
            if (event == null)
                return;
            roomType = "Event - " + Pattern.compile(".+\\.", Pattern.MULTILINE).matcher(event.getClass().getName()).replaceAll("");
            smallImage = "event";
        }
        else {
            try {
                roomType = "Modded room - " + Pattern.compile(".+\\.", Pattern.MULTILINE).matcher(AbstractDungeon.currMapNode.room.getClass().getName()).replaceAll("");
            } catch (Exception e) {
                e.printStackTrace();
                roomType = "Modded room";
            }
            smallImage = "win";
        }
        
        String portrait = AbstractDungeon.player.title.toLowerCase().replace("the", "").replaceAll(" ", "");
        
        UpdatePresence(AbstractDungeon.player.title.substring(0, 1).toUpperCase() + AbstractDungeon.player.title.substring(1) + " - A" + AbstractDungeon.ascensionLevel + (hasKeys ? "H" : "") + " - " + AbstractDungeon.player.currentHealth + "/" + AbstractDungeon.player.maxHealth + "HP", "F" + AbstractDungeon.floorNum + " - " + roomType, portraits.contains(portrait) ? portrait : "cover", smallImage, true);
    }
}