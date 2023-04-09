package seedu.address.logic.commands.reviewcommands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCards.getTypicalMasterDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

public class NextCardCommandTest {
    private Model model = new ModelManager(getTypicalMasterDeck(), new UserPrefs());

    @Test
    public void execute_nextCard_success() {
        model.reviewDeck(INDEX_FIRST, List.of(new Tag.TagName[]{Tag.TagName.HARD}));

        CommandResult expectedCommandResult = new CommandResult(NextCardCommand.MESSAGE_SUCCESS);
        assertEquals(expectedCommandResult, new NextCardCommand().execute(model));
    }

    @Test
    public void execute_noMoreNextCard_throwsCommandException() {
        model.reviewDeck(INDEX_FIFTH, List.of(new Tag.TagName[]{Tag.TagName.UNTAGGED}));

        CommandResult expectedCommandResult = new CommandResult(NextCardCommand.MESSAGE_NO_MORE_NEXT_CARD);
        assertEquals(expectedCommandResult, new NextCardCommand().execute(model));
    }
}
