package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.DisguiseDelegate;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.W;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMoveEvent implements Listener
{
    
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        
        for (Arena arena : W.arenaList)
        {
            if (arena.playersInArena.contains(player))
            {
                if (arena.gameState == ArenaState.INGAME)
                {
                    W.moveLoc.put(player, player.getLocation());
                    double maxX = Math.max(arena.pos1.getX(), arena.pos2.getX());
                    double minX = Math.min(arena.pos1.getX(), arena.pos2.getX());
                    double maxY = Math.max(arena.pos1.getY(), arena.pos2.getY());
                    double minY = Math.min(arena.pos1.getY(), arena.pos2.getY());
                    double maxZ = Math.max(arena.pos1.getZ(), arena.pos2.getZ());
                    double minZ = Math.min(arena.pos1.getZ(), arena.pos2.getZ());
                    
                    Location loc = player.getLocation();
                    if (loc.getBlockX() > maxX)
                    {
                        event.setCancelled(true);
                        player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                        player.teleport(arena.hidersWarp);
                    }
                    else if (loc.getBlockX() < minX)
                    {
                        event.setCancelled(true);
                        player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                        player.teleport(arena.hidersWarp);
                    }
                    else if (loc.getBlockZ() > maxZ)
                    {
                        event.setCancelled(true);
                        player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                        player.teleport(arena.hidersWarp);
                    }
                    else if (loc.getBlockZ() < minZ)
                    {
                        event.setCancelled(true);
                        player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                        player.teleport(arena.hidersWarp);
                    }
                    else if (loc.getBlockY() > maxY)
                    {
                        event.setCancelled(true);
                        player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                        player.teleport(arena.hidersWarp);
                    }
                    else if (loc.getBlockY() < minY)
                    {
                        if (!arena.seekers.contains(player))
                        {
                            if (W.shop.getFile().get(player.getName() + ".tokens") == null)
                            {
                                W.shop.getFile().set(player.getName() + ".tokens", 0);
                                W.shop.save();
                            }
                            int playerTokens = W.shop.getFile().getInt(
                                    player.getName() + ".tokens");
                            float addingTokens = (arena.hidersTokenWin - (((float) arena.timer / (float) arena.gameTime) * arena.hidersTokenWin));
                            W.shop.getFile().set(player.getName() + ".tokens",
                                    playerTokens + (int) addingTokens);
                            W.shop.save();
                            
                            MessageM.sendFMessage(player, ConfigC.normal_addedToken,
                                    "amount-" + (int) addingTokens);
                            
                            arena.seekers.add(player);
                            player.setFoodLevel(20);
                            ArenaHandler
                                    .sendFMessage(
                                            arena,
                                            ConfigC.normal_ingameHiderDied,
                                            "playername-" + player.getName(),
                                            "left-"
                                                    + (arena.playersInArena.size() - arena.seekers
                                                            .size()));
                        }
                        else
                        // seeker die
                        {
                            ArenaHandler.sendFMessage(arena,
                                    ConfigC.normal_ingameSeekerDied, "playername-"
                                            + player.getName(), "secs-"
                                            + arena.waitingTimeSeeker);
                        }
                        player.getInventory().clear();
                        player.updateInventory();
                        
                        if (arena.seekers.size() >= arena.playersInArena.size())
                        {
                            ArenaHandler.seekersWin(arena);
                        }
                        else
                        {
                            // DisguiseAPI.undisguiseToAll(player);
                            DisguiseDelegate.GetSingleton().UnDisguise(player);
                            W.seekertime.put(player, arena.waitingTimeSeeker);
                            player.teleport(arena.seekersWarp);
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        // event.setCancelled(true);
                        player.playEffect(loc, Effect.ENDER_SIGNAL, null);
                        player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
                        // player.teleport(arena.hidersWarp);
                    }
                }
            }
        }
    }
}
