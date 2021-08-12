package io.github.bumpjammy.SimpleTPA;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	int time; // Initialise for timer (countdown)
	int taskID; // Separates tasks
	
	public void setTimer(int amount) {
        time = amount; // Sets the timer for startTimer()
    }
	
	   public void startTimer(Player player, Player teleporter) { // Used to countdown for the /tpa
	        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	        taskID = scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
	            @Override
	            public void run() {
	            	if(time == 0) { // If countdown over
						player.sendMessage(ChatColor.GOLD + "Teleporting!");
						teleporter.sendMessage(ChatColor.GOLD + "Teleporting"); //Sends both parties teleporting message
						if(Teleport.canTP(teleporter, player)) { // Checks if it is safe to teleport
	            			teleporter.teleport(player); // Teleports Player
	            		}else {
	            			teleporter.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET + ChatColor.RED.toString() + "Unsafe teleport destination!");
	            			player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET + ChatColor.RED.toString() + "Unsafe teleport destination!"); //Tells both parties that teleporting is unsafe
	            		}
						TPArequests.remove(player.getName()); //Removes them from the tpa list
	                    stopTimer();
	                    return;
	                }else {
	                	teleporter.sendMessage(ChatColor.RED.toString() + time + ChatColor.GOLD + " seconds remaining!");
	                	player.sendMessage(ChatColor.RED.toString() + time + ChatColor.GOLD + " seconds remaining!");
	                }
	            	time = time - 1;
	            }
	        }, 0L, 20L);
	    }
	   
	   public void stopTimer() {
	        Bukkit.getScheduler().cancelTask(taskID);
	    }
	
    @Override
    public void onEnable() {
        getLogger().info("SimpleTPA is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleTPA is disabled!");
    }
    
    HashMap<String, String> TPArequests = new HashMap<String, String>();
    HashMap<String, String> TPAHERErequests = new HashMap<String, String>();
    
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(true) {
			String lowerCmd = cmd.getName().toLowerCase();
			
			switch (lowerCmd) {
			
			case "tpa":
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(args.length == 1) {
						Player TpTo = Bukkit.getPlayer(args[0]);
						if(TpTo != null) {
							if(TpTo == player) {
								player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "You can't send a tpa request to yourself!");
								return true;
							}
							if(TPArequests.containsKey(TpTo.getName())) {
								player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "This player already has an incoming tpa request! Tell them to do /tpdeny!");
								TpTo.sendMessage(player.getDisplayName() + ChatColor.GOLD + "Tried to teleport to you, but you already have an incoming request!");
							} else {
								TPArequests.put(TpTo.getName(), player.getName());
								TpTo.sendMessage(player.getDisplayName() + ChatColor.GOLD + " has requested to teleport to you!");
								TpTo.sendMessage(ChatColor.GOLD + "Do " + ChatColor.GREEN + "/tpaccept " + ChatColor.GOLD + "to accept!");
								TpTo.sendMessage(ChatColor.GOLD + "Do " + ChatColor.RED + "/tpdeny " + ChatColor.GOLD + "to deny!");
								player.sendMessage(ChatColor.GOLD + "Tp request sent to " + ChatColor.RESET + TpTo.getDisplayName());
							}
						}else {
							player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "Player does not exist!");
						}
					}else {
						player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "Invalid amount of arguments! Syntax: /tpa <player>");
					}
				}else {
					Bukkit.getLogger().severe("This commmand can only be used by players!");
				}
				break;
				
			case "tpahere":
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(args.length == 1) {
						Player TpTo = Bukkit.getPlayer(args[0]);
						if(TpTo != null) {
							if(TpTo == player) {
								player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "You can't send a tpa request to yourself!");
								return true;
							}
							if(TPAHERErequests.containsKey(TpTo.getName())) {
								player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "This player already has an incoming tpa request! Tell them to do /tpdeny!");
								TpTo.sendMessage(player.getDisplayName() + ChatColor.GOLD + "Tried to teleport you to them, but you already have an incoming request!");
							} else {
								TPAHERErequests.put(TpTo.getName(), player.getName());
								TpTo.sendMessage(player.getDisplayName() + ChatColor.GOLD + " has requested for you to teleport to them!");
								TpTo.sendMessage(ChatColor.GOLD + "Do " + ChatColor.GREEN + "/tpaccept " + ChatColor.GOLD + "to accept!");
								TpTo.sendMessage(ChatColor.GOLD + "Do " + ChatColor.RED + "/tpdeny " + ChatColor.GOLD + "to deny!");
								player.sendMessage(ChatColor.GOLD + "Tpahere request sent to " + ChatColor.RESET + TpTo.getDisplayName());
							}
						}else {
							player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "Player does not exist!");
						}
					}else {
						player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "Invalid amount of arguments! Syntax: /tpa <player>");
					}
				}else {
					Bukkit.getLogger().severe("This commmand can only be used by players!");
				}
				break;
			case "tpaccept":
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(TPArequests.containsKey(player.getName())) {
						Player teleporter = Bukkit.getPlayer(TPArequests.get(player.getName()));
						if(teleporter != null) {
							setTimer(5);
							startTimer(player, teleporter);
						}else {
							player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "Player has logged out!");
							TPArequests.remove(player.getName());
						}
					} else {
						if(TPAHERErequests.containsKey(player.getName())) {
							Player teleporter = Bukkit.getPlayer(TPAHERErequests.get(player.getName()));
							if(teleporter != null) {
								setTimer(5);
								startTimer(teleporter, player);
							}else {
								player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "Player has logged out!");
								TPAHERErequests.remove(player.getName());
							}
						}else {
							player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "No incoming requests!");
						}
					}
				}else {
					Bukkit.getLogger().severe("This commmand can only be used by players!");
				}
				break;
			case "tpdeny":
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(TPArequests.containsKey(player.getName())) {
						Player teleporter = Bukkit.getPlayer(TPArequests.get(player.getName()));
						player.sendMessage(ChatColor.RED + "Denied tpa request!");
						teleporter.sendMessage(ChatColor.RED + "Your tpa request was denied!");
						TPArequests.remove(player.getName());
					} else {
						player.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Error: " + ChatColor.RESET.toString() + ChatColor.RED + "No incoming requests!");
					}
				}else {
					Bukkit.getLogger().severe("This commmand can only be used by players!");
				}
				break;
			}
		}
		
		return true;
	}

}
