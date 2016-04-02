package lib.securebit.itemeditor.commands.settings;

import org.apache.commons.lang.Validate;

import eu.securebit.itemeditor.config.Strings;
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
		return this.layout.format("\\pre" + Strings.get(Strings.ERROR_PERMISSION_DENIED));
	}

	@Override
	public String getMessageSyntax() {
		return this.layout.format("\\pre-Syntax: %s-");
	}

	@Override
	public String getMessageOnlyPlayer() {
		return this.layout.format("\\pre" + Strings.get(Strings.ERROR_ONLY_PLAYERS_ALLOWED));
	}

	@Override
	public String getMessageDefault() {
		return this.layout.format("\\pre" + Strings.get(Strings.ERROR_ARGUMENT_NOT_FOUND));
	}
	
}
