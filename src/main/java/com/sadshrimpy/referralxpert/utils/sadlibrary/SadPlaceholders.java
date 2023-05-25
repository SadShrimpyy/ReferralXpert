package com.sadshrimpy.referralxpert.utils.sadlibrary;

public class SadPlaceholders {

    // Placeholders

    // Player's related
        private final String playerName = "%player-name%";
        private final String playerInvolved = "%player-involved" +
                "%";
        private final String playerExecutor = "%player-executor%";
        private final String actionName = "%action-name%";
    // Plugin's related
        private final String prefix = "%prefix%";
        private final String permission = "%permission%";
        private final String command = "%command%";
    // CUSTOM
       // Help
            private final String helpCurPage = "%help-cur-page%";
            private final String helpMaxPage = "%help-max-page%";


    // Getters and Setters

    // Player's related
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

    // Plugin's related
        public String getPrefix() {
            return prefix;
        }
        public String getPermission() {
            return permission;
        }
        public String getCommand() {
            return command;
        }

    // CUSTOM
        // Help
            public String getHelpCurPage() { return helpCurPage; }
            public String getHelpMaxPage() { return helpMaxPage; }
}