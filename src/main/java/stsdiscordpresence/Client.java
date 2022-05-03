package stsdiscordpresence;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordRPC;
import java.time.Instant;

public class Client {
    public static long startTime;

    public static void UpdatePresence(String details, String state, boolean showTime) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(state)
            .setDetails(details)
            .setBigImage("cover", "Slay the Spire");
        if (showTime)
            builder.setStartTimestamps(showTime ? startTime : null);
        DiscordRichPresence rich = builder.build();
        DiscordRPC.discordUpdatePresence(rich);
        System.out.println("set discord rich presence");
    }

    public static void FloorInfo() {
        String roomType;
        if (AbstractDungeon.floorNum == 0)
            roomType = "Neow";
        else if (AbstractDungeon.currMapNode.room instanceof MonsterRoomBoss)
            roomType = "Boss";
        else if (AbstractDungeon.currMapNode.room instanceof MonsterRoomElite)
            roomType = "Elite";
        else if (AbstractDungeon.currMapNode.room instanceof MonsterRoom)
            roomType = "Enemy";
        else if (AbstractDungeon.currMapNode.room instanceof RestRoom)
            roomType = "Rest site";
        else if (AbstractDungeon.currMapNode.room instanceof ShopRoom)
            roomType = "Merchant - " + AbstractDungeon.player.gold + "G";
        else if (AbstractDungeon.currMapNode.room instanceof TreasureRoom)
            roomType = "Chest";
        else if (AbstractDungeon.currMapNode.room instanceof TreasureRoomBoss)
            roomType = "Boss chest";
        else if (AbstractDungeon.currMapNode.room instanceof VictoryRoom)
            roomType = "Victory";
        else
            roomType = "Event";
        UpdatePresence(AbstractDungeon.player.title.substring(0, 1).toUpperCase() + AbstractDungeon.player.title.substring(1) + " - A" + AbstractDungeon.ascensionLevel, "F" + AbstractDungeon.floorNum + " - " + roomType, true);
    }
}