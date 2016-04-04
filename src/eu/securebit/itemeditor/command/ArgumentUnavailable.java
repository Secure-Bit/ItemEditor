package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.commands.Argument;

public class ArgumentUnavailable extends Argument<Main> {

	public ArgumentUnavailable() {
		super(Main.instance());
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Main.layout().message(sender, Strings.get(Strings.ERROR_FEATURE_UNAVAILABLE, "1.9"));
		return true;
	}

}
