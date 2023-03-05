package seedu.address.model;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.powercard.Powercard;

/**
 * The API of the Model component.
 * Serves as a way to communicate with Logic Class.
 */
public interface NewModel {
    /** {@code Predicate} that always evaluate to true */
//    Predicate<Power> PREDICATE_SHOW_ALL_DECKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //=========== ONLY WHEN DECK NOT SELECTED ========================================================================
    void addDeck();
    void deleteDeck();
    void selectDeck();


    //=========== ONLY WHEN DECK SELECTED ============================================================================

    /**
     * unselects deck
     */
    void unselectDeck();

    /**
     * Replaces selected deck data with the data in {@code deck}.
     */
    void setSelectedDeck(ReadOnlyDeck deck);

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

//    /** Returns an unmodifiable view of the filtered person list */
//    ObservableList<Person> getFilteredPersonList();
//
//    /**
//     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
//     * @throws NullPointerException if {@code predicate} is null.
//     */
//    void updateFilteredPersonList(Predicate<Person> predicate);
}
