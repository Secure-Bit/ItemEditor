package lib.securebit.itemeditor.commands.settings;

import lib.securebit.itemeditor.commands.CommandSettings;

public class SimpleCommandSettings implements CommandSettings {

	/*
	 * Notice: This file does not need to be translated!
	 * LayoutCommandSettings is used instead.
	 */
	
	private final String messagePrefix;
	private final String messageNoPermission;
	private final String messageSyntax;
	private final String messageOnlyPlayer;
	private final String messageDefault;
	
	public SimpleCommandSettings(String prefix) {
		this.messagePrefix = prefix;
		this.messageNoPermission = this.messagePrefix + "§cYou do not have the permission to do that!";
		this.messageSyntax = this.messagePrefix + "§cSyntax: %s";
		this.messageOnlyPlayer = this.messagePrefix + "§cYou have to be a player!";
		this.messageDefault = this.messagePrefix + "§cThis argument does not exist!";
	}
	
	@Override
	public String getMessageNoPermission() {
		return this.messageNoPermission;
	}

	@Override
	public String getMessageSyntax() {
		return this.messageSyntax;
	}

	@Override
	public String getMessageOnlyPlayer() {
		return this.messageOnlyPlayer;
	}

	@Override
	public String getMessageDefault() {
		return this.messageDefault;
	}
	
}
