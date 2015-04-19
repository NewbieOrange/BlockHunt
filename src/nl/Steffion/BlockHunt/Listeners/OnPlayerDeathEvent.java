package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.DisguiseDelegate;
import nl.Steffion.BlockHunt.W;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeathEvent(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        if (player != null)
        {
            for (Arena arena : W.arenaList)
            {
                if (arena.playersInArena.contains(player)
                        && arena.seekers.contains(player))
                {
                    event.setDroppedExp(0);
                    event.getDrops().clear();
                    event.setKeepInventory(true);
                    ArenaHandler.sendFMessage(arena, ConfigC.normal_ingameSeekerDied,
                            "playername-" + player.getName(), "secs-"
                                    + arena.waitingTimeSeeker);
                    player.getInventory().clear();
                    player.updateInventory();
                    
                    // DisguiseAPI.undisguiseToAll(player);
                    DisguiseDelegate.GetSingleton().UnDisguise(player);
                    W.seekertime.put(player, arena.waitingTimeSeeker);
                    player.setHealth(20.0);
                    player.teleport(arena.seekersWarp);
                    player.setGameMode(GameMode.SURVIVAL);
                    // event.setCancelled(true);
                    Location loc = player.getLocation();
                    player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                    player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                    // player.teleport(arena.hidersWarp);
                }
            }
        }
    }
}
