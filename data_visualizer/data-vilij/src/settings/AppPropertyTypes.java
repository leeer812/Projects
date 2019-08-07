package settings;

/**
 * This enumerable type lists the various application-specific property types listed in the initial set of properties to
 * be loaded from the workspace properties <code>xml</code> file specified by the initialization parameters.
 *
 * @author Ritwik Banerjee
 * @see vilij.settings.InitializationParams
 */
public enum AppPropertyTypes {

    /* resource files and folders */
    DATA_RESOURCE_PATH,
    DATA_RESOURCE_DIR,
    CSS_RESOURCE_DIR,
    CONFIG_WINDOW_CSS_DIR,
    MAIN_WINDOW_CSS_DIR,

    /* user interface icon file names */
    SCREENSHOT_ICON,
    GEAR_PNG_DIR,

    /* tooltips for user interface buttons */
    SCREENSHOT_TOOLTIP,

    /* error messages */
    RESOURCE_SUBDIR_NOT_FOUND,
    BLANK_LABEL_EXCEPTION,

    /* application-specific message titles */
    EXIT_WHILE_RUNNING_TITLE,
    EXIT_UNSAVED_TITLE,
    NO_NEW_DATA_TITLE,
    SAVE_UNSAVED_WORK_TITLE,
    SAVE_DIRECTORY_TITLE,
    NAME_ERROR_TITLE,
    NUMBER_FORMAT_TITLE,
    INVALID_FORMAT_TITLE,
    DUP_NAME_TITLE,
    LOAD_TITLE,
    LOAD_ERROR_TITLE,
    ALG_CONFIG_WINDOW_TITLE,

    /* application-specific messages */
    EXIT_WHILE_RUNNING_WARNING,
    EXIT_UNSAVED_WARNING,
    SAVE_UNSAVED_WORK,
    NO_NEW_DATA,
    NAME_ERROR_MSG,
    NUMBER_FORMAT_MSG,
    INVALID_FORMAT_MSG,
    DUP_NAME_MSG,
    BLANK_LABEL_MSG,
    DUP_NAME,
    ON_LINE,
    ERROR,
    NULL,
    INVALID_CONFIG_FORMAT_MSG,
    INVALID_CONFIG_UPDATE,
    RUN_CONFIG_ERROR_MSG,
    INVALID_CLUSTER_NUMBER,

    /* application-specific parameters */
    DATA_FILE_EXT,
    PICTURE_FILE_EXT,
    DATA_FILE_EXT_DESC,
    PICTURE_FILE_EXT_DESC,
    TEXT_AREA,
    CHECKBOX_LABEL,
    DISPLAY_BUTTON,
    SPECIFIED_FILE,
    CSS_STROKE_WIDTH_2,
    CSS_STROKE_WIDTH_0,
    CSS_STROKE_TRANSPARENT,
    CSS_BACKGROUND_TRANSPARENT,
    SERIES_AVG_NAME,
    PNG,
    ALG_RUN_CONFIG,
    CONFIG_ID,
    MAX_IT_LABEL,
    UPDATE_INT_LABEL,
    CLUSTER_NUM_LABEL,
    CONTRUN_LABEL,
    ITERATION_LABEL,
    OUT_OF_BOUNDS_LABEL,
    SUBMIT_BUTTON, RUN_BUTTON, DONE_BUTTON, BACK_BUTTON, EDIT_BUTTON, CLASS_BUTTON, CLUSTER_BUTTON, ALG_TYPE_BUTTON,
    BUTTON_ID,
    RANDOM_CLASS_RADIO, RANDOM_CLUSTER_RADIO,
    DATA_LABEL_INSTANCE_LABEL,
    DATA_LABEL_LOAD_DIR,
    DATA_LABEL_LABELS,
    LABEL_PREFIX,
    GRACEFUL_MSG,
    MORE_THAN_ONE_MSG,
    LESS_THAN_FOUR_MSG,
    ALG_TYPE_PACKAGE,
    ALG_NAME_PACKAGE,
    GET_MIN_LABELS_METHOD,
    GET_MIN_INSTANCES_METHOD
}
