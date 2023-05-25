package me.nilsb20.custombossbar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class PlayerBarManager implements CommandExecutor, Listener {

    private static final Set<PlayerBossBar> playerBossBars = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 2) {
            sender.sendMessage(ChatColor.DARK_RED + "You didn't use this command the right way!");
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            sender.sendMessage(ChatColor.DARK_RED + "This player isn't online!");
            return true;
        }

        Bukkit.getLogger().info(String.valueOf(playerBossBars.size()));

        // Get new title
        StringBuilder newTitle = new StringBuilder();
        newTitle.append(args[1]);
        for (int i = 2; i < args.length; i++) {
            newTitle.append(" ").append(args[i]);
        }

        for(PlayerBossBar playerBossBar : playerBossBars) {
            if(playerBossBar.getPlayer().equals(p)) {
                playerBossBar.updateTitleText(newTitle.toString());
                sender.sendMessage(ChatColor.GREEN + "You updated the title of the player " + p.getName() + "!");
                return true;
            }
        }

        // Create new bossbar
        PlayerBossBar playerBossBar = new PlayerBossBar(p, newTitle.toString());
        playerBossBars.add(playerBossBar);
        sender.sendMessage(ChatColor.GREEN + "You created a new bossbar for the player " + p.getName() + "!");
        return true;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        removePlayerBar(p);
    }

    /**
     * Needs to be called when the server stops so all playerbossbar are removed
     */
    public static void serverStops() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            removePlayerBar(p);
        }
    }

    /**
     * Remove the playerbossbar of a certain player
     * @param p the player the bossbar needs to be removed from
     */
    private static void removePlayerBar(Player p) {
        for(PlayerBossBar playerBossBar : playerBossBars) {
            if(playerBossBar.getPlayer().equals(p)) {
                playerBossBar.removeBar();
                playerBossBars.remove(playerBossBar);
                return;
            }
        }
    }
}
