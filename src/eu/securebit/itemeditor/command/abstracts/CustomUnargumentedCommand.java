package eu.securebit.itemeditor.command.abstracts;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.UnargumentedCommand;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public abstract class CustomUnargumentedCommand extends UnargumentedCommand {

	private final String prefix;
	
	public CustomUnargumentedCommand(String name, String prefix) {
		super(prefix == null ? name : prefix + name, new LayoutCommandSettings(Main.layout()), Main.instance());
		this.prefix = prefix;
	}
	
	@Override
	public void setAliases(String... aliases) {
		if (this.prefix == null) {
			super.setAliases(aliases);
			return;
		}
		
		for (int i = 0; i < aliases.length; i++) {
			super.setAliases(prefix + aliases[i]);
		}
	}
}
