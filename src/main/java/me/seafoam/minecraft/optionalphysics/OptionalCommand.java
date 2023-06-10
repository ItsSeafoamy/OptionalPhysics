package me.seafoam.minecraft.optionalphysics;

import me.seafoam.minecraft.seafoamy.command.Command;
import me.seafoam.minecraft.seafoamy.command.CommandExecutor;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.text.TranslatedMessage;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OptionalCommand extends Command {

	public OptionalCommand() {
		super("optionalphysics", "optionalphysics.use", "togglephysics", "physics");
	}

	@Override
	public void execute(@NotNull CommandExecutor sender, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof OnlinePlayer player) {
			UUID uuid = player.getUniqueId();

			if (!OptionalPhysics.isIgnoringPhysics(uuid)) {
				OptionalPhysics.setIgnoringPhysics(uuid, true);

				player.sendMessage(new TranslatedMessage("optionalphysics:disabled").colour(NamedTextColor.RED));
			} else {
				OptionalPhysics.setIgnoringPhysics(uuid, false);
				player.sendMessage(new TranslatedMessage("optionalphysics:enabled").colour(NamedTextColor.GREEN));
			}
		}
	}

	@Override
	public @NotNull List<String> onTabComplete(@NotNull CommandExecutor sender, @NotNull String label, @NotNull String[] args) {
		return new ArrayList<>();
	}
}
