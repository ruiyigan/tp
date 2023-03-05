package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.powercard.Powercard;

/**
 * Represents the in-memory model of the deck data.
 */
public class NewModelManager implements NewModel {
    private static final Logger logger = LogsCenter.getLogger(NewModelManager.class);

    private int selectedDeckIndex = -1; // -1 means unselected
    private final Decks[] allDecks;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given decks and userPrefs.
     */
    public NewModelManager(ReadOnlyDeck[] allDecks, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(allDecks, userPrefs);

        logger.fine("Initializing with all decks: " + allDecks + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    // creates empty array of decks
//    public NewModelManager() {
//        this(new AddressBook(), new UserPrefs());
//    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== ONLY WHEN DECK NOT SELECTED ========================================================================
    public void addDeck() {
        // add deck command, creating a new deck and adding it to the list of decks
    }
    public void deleteDeck() {
        // delete deck command, delete deck from list of decks
    }
    public void selectDeck(int newIndex) {
        this.selectedDeckIndex = newIndex;
    }


    //=========== ONLY WHEN DECK SELECTED ============================================================================

    /**
     * unselects deck
     */
    public void unselectDeck() {
        this.selectedDeckIndex = -1;
    }

    /**
     * Replaces selected deck data with the data in {@code deck}.
     */
    void setSelectedDeck(ReadOnlyDeck deck) {
        // based on deck index, replaces selected deck data
    }

    /** Returns the selected Deck */
    ReadOnlyDeck getSelectedDeck();

    /**
     * Returns true if a powercard with the same identity as {@code powercard} exists in the selected deck.
     */
    boolean hasPowercard(Powercard powercard);

    /**
     * Deletes the given powercard.
     * The powercard must exist in the selected deck.
     */
    void deletePowercard(Powercard target);

    /**
     * Adds the given powercard in the selected deck.
     * {@code powercard} must not already exist in the selected deck.
     */
    void addPowercard(Powercard powercard);

    /**
     * Replaces the given powercard {@code target} with {@code editedPowercard}.
     * {@code target} must exist in the selected deck.
     * The powercard identity of {@code editedPowercard} must not be the same as another existing powercard in the
     * selected deck.
     */
    void setPowercard(Powercard target, Powercard editedPowercard);

}
