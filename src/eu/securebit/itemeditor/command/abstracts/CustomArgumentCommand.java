package eu.securebit.itemeditor.command.abstracts;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.ArgumentedCommand;
import lib.securebit.itemeditor.commands.settings.LayoutCommandSettings;

public abstract class CustomArgumentCommand extends ArgumentedCommand {

	private final String prefix;
	
	public CustomArgumentCommand(String name, String prefix) {
		super(prefix == null ? name : prefix + name, new LayoutCommandSettings(Main.layout()), Main.instance());
		this.prefix = prefix;
	}
	
	@Override
	public void setAliases(String... aliases) {
		if (this.prefix == null) {
			super.setAliases(aliases);
			return;
		}
		
		String[] newAliases = new String[aliases.length];
		for (int i = 0; i < newAliases.length; i++) {
			newAliases[i] = this.prefix + aliases[i];
		}
		
		super.setAliases(newAliases);
	}
}
