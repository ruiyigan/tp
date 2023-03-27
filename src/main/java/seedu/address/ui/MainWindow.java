package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final Text MAIN_TITLE = new Text("Main");
    private static final Text REVIEW_TITLE = new Text("Review");
    private static final ObservableList<String> EMPTY_TITLE = FXCollections.observableArrayList("");

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private UiPart<Region> leftPanel;
    private UiPart<Region> rightTitle;
    private ResultDisplay resultDisplay;
    private final HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane rightPanelPlaceholder;

    @FXML
    private StackPane rightPanelTitlePlaceholder;

    @FXML
    private StackPane leftPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TextFlow titlePanel;



    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        titlePanel.getChildren().add(MAIN_TITLE);

        rightTitle = new DeckNamePanel(logic.getDeckNameList());
        rightPanelTitlePlaceholder.getChildren().add(rightTitle.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredCardList(), false);
        rightPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        leftPanel = new DeckListPanel(logic.getFilteredDeckList(), false);
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMasterDeckFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Creates a confirmation pop-up
     */
    public void handleClear() {
        /* Creates a confirmation alert */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to clear all data?");

        /* Creates buttons */
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        /* Checks user's answer */
        Optional<ButtonType> answer = alert.showAndWait();
        answer.filter(response -> response == buttonYes).ifPresent(unused -> logic.factoryReset());
    }

    /**
     * Shows the review stats panel.
     */
    public void handleStartReview() {
        leftPanel = new ReviewStatsPanel(logic.getReviewStatsList());
        leftPanelPlaceholder.getChildren().clear();
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getReviewCardList(), true);

        titlePanel.getChildren().clear();
        titlePanel.getChildren().add(REVIEW_TITLE);

        rightPanelTitlePlaceholder.getChildren().clear();
        rightTitle = new DeckNamePanel(EMPTY_TITLE);
        rightPanelTitlePlaceholder.getChildren().add(rightTitle.getRoot());
    }

    /**
     * Shows the deck list panel.
     */
    public void handleEndReview() {
        updateDeckTitle();

        leftPanel = new DeckListPanel(logic.getFilteredDeckList(), false);
        leftPanelPlaceholder.getChildren().clear();
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredCardList(), true);

        titlePanel.getChildren().clear();
        titlePanel.getChildren().add(MAIN_TITLE);
    }

    /**
     * Updates the deck Title.
     */
    public void updateDeckTitle() {
        rightPanelTitlePlaceholder.getChildren().clear();
        rightTitle = new DeckNamePanel(logic.getDeckNameList());
        rightPanelTitlePlaceholder.getChildren().add(rightTitle.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isClear()) {
                handleClear();
            }

            if (commandResult.isStartReview()) {
                handleStartReview();
            }

            if (commandResult.isEndReview()) {
                handleEndReview();
            }

            if (commandResult.isSelectDeck() || commandResult.isUnselectDeck()) {
                updateDeckTitle();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
