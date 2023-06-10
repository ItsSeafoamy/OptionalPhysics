package me.seafoam.minecraft.optionalphysics;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.UUID;

public class OptionalListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		OptionalPhysics.setIgnoringPhysics(event.getPlayer().getUniqueId(), false);
	}

	public void physicsEvent(BlockEvent event) {
		for (UUID uuid : OptionalPhysics.getIgnoringPhysics()) {
			Player p = Bukkit.getPlayer(uuid);

			if (p.getLocation().getWorld().getUID().equals(event.getBlock().getLocation().getWorld().getUID())) {
				if (p.getLocation().distanceSquared(event.getBlock().getLocation()) <= OptionalPhysics.getDistance()) {
					if (event instanceof Cancellable) {
						((Cancellable) event).setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event) {
		physicsEvent(event);
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		physicsEvent(event);
	}

	@EventHandler
	public void onBlockForm(BlockFormEvent event) {
		physicsEvent(event);
	}

	@EventHandler
	public void onBlockBreakBlock(BlockBreakBlockEvent event) {
		physicsEvent(event);
	}

	@EventHandler
	public void onBlockDestroy(BlockDestroyEvent event) {
		physicsEvent(event);
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		for (UUID uuid : OptionalPhysics.getIgnoringPhysics()) {
			Player p = Bukkit.getPlayer(uuid);

			if (p.getLocation().getWorld().getUID().equals(event.getBlock().getLocation().getWorld().getUID())) {
				if (p.getLocation().distanceSquared(event.getBlock().getLocation()) <= OptionalPhysics.getDistance()) {
					if (event.getBlock().getType().hasGravity()) {
						event.setCancelled(true);
						event.getBlock().getState().update(true, false);
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent event) {
		if (OptionalPhysics.isPreventBlockFades()) event.setCancelled(true);
		else physicsEvent(event);
	}
}