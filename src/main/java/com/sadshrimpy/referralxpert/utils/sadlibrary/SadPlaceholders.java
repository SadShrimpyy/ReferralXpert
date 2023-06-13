package com.sadshrimpy.referralxpert.utils.sadlibrary;

public class SadPlaceholders {

    // Placeholders

    /** Player's related */
        private final String playerName = "%player-name%";
        private final String playerInvolved = "%player-involved%";
        private final String playerExecutor = "%player-executor%";
        private final String actionName = "%action-name%";
    /** Plugin's related */
        private final String prefix = "%prefix%";
        private final String permission = "%permission%";
        private final String command = "%command%";
    /** Custom */
       // Help
            // Pages
            private final String helpCurPage = "%help-cur-page%";
            private final String helpMaxPage = "%help-max-page%";
            private final String helpPrevPage = "%help-prev-page%";
            private final String helpNextPage = "%help-next-page%";
            // Buttons
            private final String helpBtnNext = "%help-button-next%";
            private final String helpBtnPrev = "%help-button-previous%";
        // Referral
            // Code
            private final String code = "%code%";
            private final String codeMaxUsages = "%code-max-usages%";


    /** Player's related */
        public String getPlayerName() {
            return playerName;
        }
        public String getPlayerInvolved() {
            return playerInvolved;
        }
        public String getPlayerExecutor() {
            return playerExecutor;
        }
        public String getActionName() {
            return actionName;
        }

    /** Plugin's related */
        public String getPrefix() {
            return prefix;
        }
        public String getPermission() {
            return permission;
        }
        public String getCommand() {
            return command;
        }

    /** Custom */
        // Help
            // Pages
            public String getHelpCurPage() { return helpCurPage; }
            public String getHelpMaxPage() { return helpMaxPage; }
            public String getHelpPrevPage() {
                return helpPrevPage;
            }
            public String getHelpNextPage() {
                return helpNextPage;
            }
            public String getHelpBtnNext() {
                return helpBtnNext;
            }
            // Buttons
            public String getHelpBtnPrev() {
                return helpBtnPrev;
            }
        // Referral
            // Code
            public String getCode() {
                return code;
            }
            public String getCodeMaxUsages() {
                return codeMaxUsages;
            }
}