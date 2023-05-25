package me.nilsb20.custombossbar;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerBossBar {

    private final Player p;             // The player the bossbar will be shown to
    private final NamespacedKey key;    // The Namespaced key of the bossbar
    private final BossBar bossBar;      // The bossbar of the player
    private String barText;             // The text the bossbar will show

    private final String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890:| !?"; // All the characters that are allowed to be used in the bossbar
    private final BarColor barColor = BarColor.PINK; // The Color of the bossbar. This color shouldn't have a texture in the resourcepack
    private final Character characterBackground = '\uE000';
    private final Character leftSideBar = '\uE001';
    private final Character rightSideBar = '\uE002';

    private final Character negativeSpacedCharacter1 = '\uF801';
    private final Character negativeSpacedCharacter3 = '\uF803';
    private final Character negativeSpacedCharacter4 = '\uF804';
    private final Character negativeSpacedCharacter5 = '\uF805';
    private final Character negativeSpacedCharacter6 = '\uF806';
    private final Character negativeSpacedCharacter7 = '\uF807';

    public PlayerBossBar(@NotNull Player p, @NotNull String barText) {
        this.p = p;
        this.barText = barText;
        this.key = new NamespacedKey(CustomBossbar.getInstance(), p.getName() + UUID.randomUUID());

        BarFlag temporaryBarStyle = BarFlag.CREATE_FOG; // This flag will be removed from the bossbar later
        this.bossBar = Bukkit.createBossBar(key, null, barColor, BarStyle.SOLID, temporaryBarStyle);

        //Set bossbar values to be correct and make sure it shows right text
        bossBar.setProgress(0);
        bossBar.setVisible(true);
        bossBar.removeFlag(temporaryBarStyle);
        bossBar.setTitle(generateTitle());
        bossBar.addPlayer(p);
    }

    /**
     * Update the title of a player and make it show in the players bossbar
     * @param text  the new text that will be shown in the bossbar
     */
    public void updateTitleText(String text) {
        barText = text;
        bossBar.setTitle(generateTitle());
    }

    /**
     * Remove the playerbossbar
     */
    public void removeBar() {
        bossBar.removeAll();
        Bukkit.removeBossBar(key);
    }

    /**
     * Generate the title of the player's bossbar as a string
     * @return  the string that should be the name of the bossbar
     */
    private String generateTitle() {
        StringBuilder title = new StringBuilder();
        title.append(String.valueOf(leftSideBar)).append(String.valueOf(negativeSpacedCharacter1));

        // Go through all characters and add the right sequence to the title
        for(int i = 0; i < barText.length(); i++) {
            char character = barText.charAt(i);
            try {
                title.append(getCharactersPerLetter(character));
            } catch(IllegalArgumentException e) {
                return "Illegal Character!";
            }
        }
        title.append(rightSideBar);
        return title.toString();
    }

    /**
     * Get the character sequence for a certain character that can be used in the title of a players bossbar
     * @param character the character of which you want to get the character squence
     * @return  the character sequence as a string
     * @throws IllegalArgumentException when a character that isn't allowed this exception will be thrown
     */
    private String getCharactersPerLetter(char character) throws IllegalArgumentException {
        if(!allowedCharacters.contains(String.valueOf(character))) {
            throw new IllegalArgumentException("Can't get string for character!");
        }
        String background = String.valueOf(negativeSpacedCharacter1) + characterBackground;

        switch(character) {
            case ' ':
                return characterBackground + background + background + background + negativeSpacedCharacter1; // space is special case
            case 'i':
            case ':':
            case '|':
            case '!':
                return characterBackground + background + background + negativeSpacedCharacter3 + character;
            case 'l':
                return characterBackground + background + background + background + negativeSpacedCharacter4 + character;
            case 't':
            case 'I':
                return characterBackground + background + background + background + background + negativeSpacedCharacter5 + character;
            case 'f':
            case 'k':
                return characterBackground + background + background + background + background + background + negativeSpacedCharacter6 + character;
            default:
                return characterBackground + background + background + background + background + background + background + negativeSpacedCharacter7 + character;
        }
    }

    public Player getPlayer() {
        return p;
    }
}
