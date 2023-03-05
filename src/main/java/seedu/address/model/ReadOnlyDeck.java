package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.powercard.Powercard;

/**
 * Unmodifiable view of a deck
 */
public interface ReadOnlyDeck {

    /**
     * Returns an unmodifiable view of the powercards list.
     * This list will not contain any duplicate powercard.
     */
    ObservableList<Powercard> getPowercardList();

}
