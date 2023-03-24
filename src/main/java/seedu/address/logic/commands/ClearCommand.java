package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the master deck.
 */
public class ClearCommand extends Command { //todo: this command is dangerous and should warn users in the future

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Please confirm before continue!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new ClearCommandResult(MESSAGE_SUCCESS);
    }
}
