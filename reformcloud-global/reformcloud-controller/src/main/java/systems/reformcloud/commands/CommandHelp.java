/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.commands;

import systems.reformcloud.ReformCloudController;
import systems.reformcloud.ReformCloudLibraryService;
import systems.reformcloud.commands.utility.Command;
import systems.reformcloud.commands.utility.CommandSender;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @author _Klaro | Pasqual K. / created on 08.12.2018
 */

public final class CommandHelp extends Command implements Serializable {

    private final DecimalFormat decimalFormat = new DecimalFormat("##.###");

    public CommandHelp() {
        super("help", "Get help", null, new String[]{"?", "ask", "ls"});
    }

    @Override
    public void executeCommand(CommandSender commandSender, String[] args) {
        commandSender.sendMessage("The following Commands are registered:");
        ReformCloudController.getInstance().getCommandManager().commands().forEach(
            command -> commandSender.sendMessage(
                "   - " + command.getName() + " | Aliases: §e" + Arrays.asList(command.getAliases())
                    + "§r | Description: §3" + command.getDescription() + "§r | Permission: §e" + (
                    command.getPermission() == null ? "none" : command.getPermission())));

        ReformCloudController.getInstance().getColouredConsoleProvider().emptyLine();

        commandSender.sendMessage("Ram: §e" + decimalFormat.format(
            ReformCloudLibraryService.bytesToMB(ReformCloudLibraryService.usedMemorySystem()))
            + "MB/" + decimalFormat.format(
            ReformCloudLibraryService.bytesToMB(ReformCloudLibraryService.maxMemorySystem()))
            + "MB");
        commandSender.sendMessage(
            "CPU (System/Internal): §e" + decimalFormat.format(ReformCloudLibraryService.cpuUsage())
                + "/" + decimalFormat.format(ReformCloudLibraryService.internalCpuUsage()));
        commandSender.sendMessage("Threads: §e" + Thread.getAllStackTraces().size());
        commandSender.sendMessage(
            "§bFor further information please contact us on our Discord (\"https://discord.gg/uskXdVZ\")");
    }
}
