package eu.securebit.itemeditor.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.itemeditor.Main;
import eu.securebit.itemeditor.command.abstracts.CustomArgumentCommand;
import eu.securebit.itemeditor.config.Strings;
import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.Argument;
import lib.securebit.itemeditor.commands.DefaultExecutor;

public class CommandPotion extends CustomArgumentCommand implements DefaultExecutor {

	public CommandPotion() {
		super("potion", Main.instance().getCommandPrefix());
		
		this.setAliases("modifypotion");
		this.setExecutor(this);
		this.setPermission("ie.potion");
		
		this.registerArgument("clear", new ArgumentPotionClear(this));
		this.registerArgument("addeffect", new ArgumentPotionAddEffect(this));
		this.registerArgument("rmeffect", new ArgumentPotionRemoveEffect(this));
		
		Argument<Main> argumentColor;
		if (Main.getMinecraftVersion() >= 19) {
			argumentColor = new ArgumentPotionColor(this);
		} else {
			argumentColor = new ArgumentUnavailable();
		}
		
		this.registerArgument("color", argumentColor);
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout().clone();
		layout.begin();
		layout.line(Strings.get(Strings.USAGE_COMMAND_POTION_CLEAR, this.getName()));
		layout.line(Strings.get(Strings.USAGE_COMMAND_POTION_ADDEFFECT, this.getName()));
		layout.line(Strings.get(Strings.USAGE_COMMAND_POTION_REMOVEEFFECT, this.getName()));
		
		if (Main.getMinecraftVersion() >= 19) {
			layout.line(Strings.get(Strings.USAGE_COMMAND_POTION_COLOR, this.getName()));
		}
		
		layout.commit(sender);
		return true;
	}

}
