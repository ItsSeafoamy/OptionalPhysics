package me.seafoam.minecraft.optionalphysics;

import lombok.Getter;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.text.TranslatedMessage;
import me.seafoam.minecraft.seafoamy.util.ConfigManager;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OptionalPhysics extends JavaPlugin {

	@Getter private static final List<UUID> ignoringPhysics = new ArrayList<>();

	@Getter private static double distance;
	@Getter private static boolean preventBlockFades;

	@Override
	public void onEnable() {
		Seafoamy.loadLanguages(this, "optionalphysics");

		Config config = ConfigManager.load(getDataFolder(), "config", Config.class);
		distance = Math.pow(config.getDistance(), 2);
		preventBlockFades = config.isPreventBlockFades();

		Seafoamy.getCommandManager().registerCommand("optionalphysics", new OptionalCommand());

		Bukkit.getPluginManager().registerEvents(new OptionalListener(), this);

		if (config.isActionbarReminder()) {
			Bukkit.getScheduler().runTaskTimer(this, () -> {
				for (UUID uuid : ignoringPhysics) {
					OnlinePlayer player = Seafoamy.getPlayerManager().getOnlinePlayer(uuid);

					if (player != null) {
						player.sendActionBar(new TranslatedMessage("optionalphysics:actionbar").colour(NamedTextColor.GOLD));
					}
				}
			}, 0, 20);
		}
	}

	public static boolean isIgnoringPhysics(UUID uuid) {
		return ignoringPhysics.contains(uuid);
	}

	public static void setIgnoringPhysics(UUID uuid, boolean ignoring) {
		if (ignoring) {
			ignoringPhysics.add(uuid);
		} else {
			ignoringPhysics.remove(uuid);
		}
	}
}