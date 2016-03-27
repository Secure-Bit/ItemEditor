package lib.securebit.itemeditor.commands;

public abstract interface CommandSettings {
	
	public abstract String getMessageNoPermission();

	public abstract String getMessageSyntax();

	public abstract String getMessageOnlyPlayer();

	public abstract String getMessageDefault();
	
}
