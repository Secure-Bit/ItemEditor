package lib.securebit.itemeditor.commands.settings;

import org.apache.commons.lang.Validate;

import lib.securebit.itemeditor.InfoLayout;
import lib.securebit.itemeditor.commands.CommandSettings;

public final class LayoutCommandSettings implements CommandSettings {

	private final InfoLayout layout;

	public LayoutCommandSettings(InfoLayout layout) {
		Validate.notNull(layout);
		
		this.layout = layout;
	}
	
	@Override
	public String getMessageNoPermission() {
		return this.layout.format("\\pre-You do not have the permission to do that!-");
	}

	@Override
	public String getMessageSyntax() {
		return this.layout.format("\\pre-Syntax: %s-");
	}

	@Override
	public String getMessageOnlyPlayer() {
		return this.layout.format("\\pre-You have to be a player!-");
	}

	@Override
	public String getMessageDefault() {
		return this.layout.format("\\pre-This argument does not exist!-");
	}
	
}
