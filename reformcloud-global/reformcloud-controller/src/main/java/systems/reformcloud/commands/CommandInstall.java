/*
  Copyright © 2019 Pasqual K. | All rights reserved
 */

package systems.reformcloud.commands;

import systems.reformcloud.ReformCloudController;
import systems.reformcloud.commands.utility.Command;
import systems.reformcloud.commands.utility.CommandSender;
import systems.reformcloud.utility.files.DownloadManager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author _Klaro | Pasqual K. / created on 06.04.2019
 */

public final class CommandInstall extends Command implements Serializable {

    public CommandInstall() {
        super("install", "Installs an addon", "reformcloud.command.install", new String[0]);
    }

    @Override
    public void executeCommand(CommandSender commandSender, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("signs")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudSigns.jar",
                    "reformcloud/addons/ReformCloudSigns.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("discord")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudDiscord.jar",
                    "reformcloud/addons/ReformCloudDiscordBot.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("permissions")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudPermissions.jar",
                    "reformcloud/addons/ReformCloudPermissions.jar");
                commandSender.sendMessage("Download was completed successfully");
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("proxy")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudProxy.jar",
                    "reformcloud/addons/ReformCloudProxy.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("parameters")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudParameters.jar",
                    "reformcloud/addons/ReformCloudParameters.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("autoicon")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudAutoIcon.jar",
                    "reformcloud/addons/ReformCloudAutoIcon.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("properties")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudProperties.jar",
                    "reformcloud/addons/ReformCloudProperties.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("mobs")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudMobs.jar",
                    "reformcloud/addons/ReformCloudMobs.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("cloudflare")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudCloudFlare.jar",
                    "reformcloud/addons/ReformCloudCloudFlare.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            } else if (args[0].equalsIgnoreCase("backup")) {
                DownloadManager.downloadSilentAndDisconnect(
                    "https://dl.reformcloud.systems/addons/ReformCloudBackup.jar",
                    "reformcloud/addons/ReformCloudBackup.jar");
                commandSender.sendMessage(
                    ReformCloudController.getInstance().getLoadedLanguage().getDownload_success()
                );
                ReformCloudController.getInstance().reloadAllSave();
                return;
            }
        }

        commandSender.sendMessage(
            "install <autoicon, backup, cloudflare, discord, mobs, parameters, permissions, proxy, properties, signs>");
    }

    @Override
    public List<String> complete(String commandLine, String[] args) {
        List<String> out = new LinkedList<>();

        if(args.length == 0) {
            out.addAll(asList("AUTOICON", "BACKUP", "CLOUDFLARE", "DISCORD", "MOBS", "PARAMETERS", "PERMISSIONS",
                "PROXY", "PROPERTIES", "SIGNS"));
        }

        return out;
    }

}
