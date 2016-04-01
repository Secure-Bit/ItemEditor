package eu.securebit.itemeditor.command.abstracts;

import eu.securebit.itemeditor.Main;
import lib.securebit.itemeditor.commands.Argument;
import lib.securebit.itemeditor.commands.BasicCommand;

public abstract class CustomArgument extends Argument<Main> {

	private final BasicCommand command;
	
	public CustomArgument(BasicCommand command) {
		super(Main.instance());
		this.command = command;
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean isOnlyForPlayer() {
		return true;
	}

	public final BasicCommand getCommand() {
		return this.command;
	}
}
