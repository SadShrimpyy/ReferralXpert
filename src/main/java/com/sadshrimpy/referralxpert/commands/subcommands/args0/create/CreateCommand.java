package com.sadshrimpy.referralxpert.commands.subcommands.args0.create;

import com.sadshrimpy.referralxpert.commands.CommandSyntax;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class CreateCommand implements CommandSyntax {

    private String[] cmdArgs;

    public CreateCommand(String[] strings) {
        cmdArgs = strings;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission(String[] args) {
        return sadLibrary.permissions().getCreate();
    }

    @Override
    public boolean hasSubcommands() { return true; }

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void perform(CommandSender sender) {
        System.out.println(Arrays.toString(cmdArgs));
    }
}
