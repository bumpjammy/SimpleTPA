package io.github.bumpjammy.SimpleTPA;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import net.minecraft.world.level.block.DoubleBlockFinder.BlockType;

public class Teleport {
	public static boolean canTP(Player teleporter, Player teleportee) {
		if(teleportee.isFlying()) {
			return false;
		}else {
			if(teleportee.getLocation().getBlock().getType() == Material.LAVA || teleportee.getEyeLocation().getBlock().getType() == Material.LAVA || teleportee.getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty()) {
				return false;
			}else {
				return true;
			}
		}
	}
}
