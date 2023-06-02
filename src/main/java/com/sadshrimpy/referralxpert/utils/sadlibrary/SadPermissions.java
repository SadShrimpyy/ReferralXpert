package com.sadshrimpy.referralxpert.utils.sadlibrary;

public class SadPermissions {

    // Permissions
    // DEFAULTS
        private final String completer = "referralxpert.completer";
        private final String reload = "referralxpert.reload";
        private final String help = "referralxpert.help";

    // CUSTOMS
        private final String get = "referralxpert.get";
        private final String use = "referralxpert.use";
        private final String create = "referralxpert.give";
        private final String giveAll = "referralxpert.give.all";
    // Getters / Setters
    // DEFAULTS
        public String getHelp() {
            return this.help;
        }
        public String getReload() {
            return this.reload;
        }
        public String getCompleter() {
            return this.completer;
    }
    // CUSTOMS
        public String getGet() {
            return this.get;
        }
        public String getUse() {
            return this.use;
        }
        public String getCreate() {
            return this.create;
        }public String getGiveAll() {
            return this.giveAll;
        }

}