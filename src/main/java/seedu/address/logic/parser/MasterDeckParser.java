package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DECK_SELECTED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_IN_REVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_REVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_DECK_SELECTED;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.cardcommands.AddCommand;
import seedu.address.logic.commands.cardcommands.DeleteCommand;
import seedu.address.logic.commands.cardcommands.EditCommand;
import seedu.address.logic.commands.deckcommands.AddDeckCommand;
import seedu.address.logic.commands.deckcommands.DeleteDeckCommand;
import seedu.address.logic.commands.deckcommands.EditDeckCommand;
import seedu.address.logic.commands.deckcommands.SelectDeckCommand;
import seedu.address.logic.commands.deckcommands.UnselectDeckCommand;
import seedu.address.logic.commands.reviewcommands.EndReviewCommand;
import seedu.address.logic.commands.reviewcommands.FlipCardCommand;
import seedu.address.logic.commands.reviewcommands.MarkCorrectCommand;
import seedu.address.logic.commands.reviewcommands.MarkWrongCommand;
import seedu.address.logic.commands.reviewcommands.NextCardCommand;
import seedu.address.logic.commands.reviewcommands.PreviousCardCommand;
import seedu.address.logic.commands.reviewcommands.ReviewCommand;
import seedu.address.logic.commands.reviewcommands.SetNumCardsPerReviewCommand;
import seedu.address.logic.commands.reviewcommands.TagCardDuringReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class MasterDeckParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private String commandWord;
    private String arguments;


    /**
     * Parses the command word and the arguments from user input String.
     *
     * @param userInput The String input from user
     * @throws ParseException If the user input does not conform to the expected format
     */
    private void updateCommandWordAndArguments(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        this.commandWord = matcher.group("commandWord");
        this.arguments = matcher.group("arguments");
    }

    /**
     * Parses user input into command for execution when no deck is selected.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommandInMainUnselectedMode(String userInput) throws ParseException {
        updateCommandWordAndArguments(userInput);

        switch (commandWord) {

        // Deck-related Commands
        case AddDeckCommand.COMMAND_WORD:
            return new AddDeckCommandParser().parse(arguments);

        case DeleteDeckCommand.COMMAND_WORD:
            return new DeleteDeckCommandParser().parse(arguments);

        case EditDeckCommand.COMMAND_WORD:
            return new EditDeckCommandParser().parse(arguments);

        case SelectDeckCommand.COMMAND_WORD:
            return new SelectDeckCommandParser().parse(arguments);

        case UnselectDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NO_DECK_SELECTED, UnselectDeckCommand.COMMAND_WORD));

        // Card-related Commands
        case AddCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NO_DECK_SELECTED, AddCommand.COMMAND_WORD));

        case EditCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NO_DECK_SELECTED, EditCommand.COMMAND_WORD));

        case DeleteCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NO_DECK_SELECTED, DeleteCommand.COMMAND_WORD));

        // Review-related Commands
        case ReviewCommand.COMMAND_WORD:
            return new ReviewCommandParser().parse(arguments);

        case SetNumCardsPerReviewCommand.COMMAND_WORD:
            return new SetNumCardsPerReviewCommandParser().parse(arguments);

        case FlipCardCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NOT_IN_REVIEW, FlipCardCommand.COMMAND_WORD));

        case PreviousCardCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NOT_IN_REVIEW, PreviousCardCommand.COMMAND_WORD));

        case NextCardCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NOT_IN_REVIEW, NextCardCommand.COMMAND_WORD));

        case MarkCorrectCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NOT_IN_REVIEW, MarkCorrectCommand.COMMAND_WORD));

        case MarkWrongCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_NOT_IN_REVIEW, MarkWrongCommand.COMMAND_WORD));

        case TagCardDuringReviewCommand.COMMAND_WORD:
            return new TagCardDuringReviewCommandParser().parse(arguments);

        case EndReviewCommand.COMMAND_WORD:
            return new EndReviewCommand();

        // Other commands
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution when a deck is selected.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommandInMainSelectedMode(String userInput) throws ParseException {
        updateCommandWordAndArguments(userInput);

        switch (commandWord) {

        // Deck-related Commands
        case AddDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_DECK_SELECTED, AddDeckCommand.COMMAND_WORD));

        case DeleteDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_DECK_SELECTED, DeleteDeckCommand.COMMAND_WORD));

        case EditDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_DECK_SELECTED, EditDeckCommand.COMMAND_WORD));

        case SelectDeckCommand.COMMAND_WORD:
            return new SelectDeckCommandParser().parse(arguments);

        case UnselectDeckCommand.COMMAND_WORD:
            return new UnselectDeckCommand();

        // Card-related Commands
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        // Review-related Commands
        case ReviewCommand.COMMAND_WORD:
            return new ReviewCommandParser().parse(arguments);

        case SetNumCardsPerReviewCommand.COMMAND_WORD:
            return new SetNumCardsPerReviewCommandParser().parse(arguments);

        // Other Commands
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ClearCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_DECK_SELECTED, ClearCommand.COMMAND_WORD));

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution when a review is underway.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommandInReviewMode(String userInput) throws ParseException {
        updateCommandWordAndArguments(userInput);

        switch (commandWord) {

        // Deck-related Commands
        case AddDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, AddDeckCommand.COMMAND_WORD));

        case DeleteDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, DeleteDeckCommand.COMMAND_WORD));

        case EditDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, EditDeckCommand.COMMAND_WORD));

        case SelectDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, SelectDeckCommand.COMMAND_WORD));

        case UnselectDeckCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, UnselectDeckCommand.COMMAND_WORD));

        // Card-related Commands
        case AddCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, AddCommand.COMMAND_WORD));

        case EditCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, EditCommand.COMMAND_WORD));

        case DeleteCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, DeleteCommand.COMMAND_WORD));

        // Review-related Commands
        case ReviewCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, ReviewCommand.COMMAND_WORD));

        case SetNumCardsPerReviewCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, SetNumCardsPerReviewCommand.COMMAND_WORD));

        case FlipCardCommand.COMMAND_WORD:
            return new FlipCardCommand();

        case PreviousCardCommand.COMMAND_WORD:
            return new PreviousCardCommand();

        case NextCardCommand.COMMAND_WORD:
            return new NextCardCommand();

        case MarkCorrectCommand.COMMAND_WORD:
            return new MarkCorrectCommand();

        case MarkWrongCommand.COMMAND_WORD:
            return new MarkWrongCommand();

        case TagCardDuringReviewCommand.COMMAND_WORD:
            return new TagCardDuringReviewCommandParser().parse(arguments);

        case EndReviewCommand.COMMAND_WORD:
            return new EndReviewCommand();

        // Other commands
        case HelpCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, HelpCommand.COMMAND_WORD));

        case ExitCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, ExitCommand.COMMAND_WORD));

        case ClearCommand.COMMAND_WORD:
            throw new ParseException(String.format(MESSAGE_IN_REVIEW, ClearCommand.COMMAND_WORD));

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
