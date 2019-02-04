/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.commands;

import systems.reformcloud.ReformCloudController;
import systems.reformcloud.commands.interfaces.Command;
import systems.reformcloud.commands.interfaces.CommandSender;
import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.meta.client.Client;
import systems.reformcloud.meta.info.ProxyInfo;
import systems.reformcloud.meta.info.ServerInfo;
import systems.reformcloud.meta.proxy.ProxyGroup;
import systems.reformcloud.meta.server.ServerGroup;
import systems.reformcloud.netty.out.PacketOutStartGameServer;
import systems.reformcloud.netty.out.PacketOutStartProxy;
import systems.reformcloud.netty.out.PacketOutStopProcess;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author _Klaro | Pasqual K. / created on 09.12.2018
 */

public final class CommandProcess implements Command {
    private final DecimalFormat decimalFormat = new DecimalFormat("##.###");

    @Override
    public void executeCommand(CommandSender commandSender, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage("process stop <name>");
            commandSender.sendMessage("process start <group-name>");
            commandSender.sendMessage("process list");
            return;
        }

        switch (args[0]) {
            case "stop": {
                if (args.length == 2) {
                    if (ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().getRegisteredProxyByName(args[1]) != null) {
                        final ProxyInfo proxyInfo = ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().getRegisteredProxyByName(args[1]);
                        ReformCloudController.getInstance().getChannelHandler().sendPacketAsynchronous(proxyInfo.getCloudProcess().getClient(), new PacketOutStopProcess(proxyInfo.getCloudProcess().getName()));
                        commandSender.sendMessage("Trying to stop " + proxyInfo.getCloudProcess().getName() + "...");
                    } else if (ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().getRegisteredServerByName(args[1]) != null) {
                        final ServerInfo serverInfo = ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().getRegisteredServerByName(args[1]);
                        ReformCloudController.getInstance().getChannelHandler().sendPacketAsynchronous(serverInfo.getCloudProcess().getClient(), new PacketOutStopProcess(serverInfo.getCloudProcess().getName()));
                        commandSender.sendMessage("Trying to stop " + serverInfo.getCloudProcess().getName() + "...");
                    } else
                        commandSender.sendMessage("This Server or Proxy is not connected to controller");
                    break;
                } else {
                    commandSender.sendMessage("process stop <name>");
                }
            }
            case "list": {
                List<Client> connectedClients = new ArrayList<>();
                ReformCloudController.getInstance().getInternalCloudNetwork()
                        .getClients()
                        .values()
                        .stream()
                        .filter(client -> client.getClientInfo() != null)
                        .forEach(client -> connectedClients.add(client));

                commandSender.sendMessage("The following Clients are connected: ");
                connectedClients.forEach(e -> {
                    commandSender.sendMessage("    - " + e.getName() + " | Host=" + e.getIp() + " | Memory-Usage=" + e.getClientInfo().getUsedMemory() + "MB/" + e.getClientInfo().getMaxMemory() + "MB | Processors: " + e.getClientInfo().getCpuCoresSystem() + " | CPU-Usage: " + decimalFormat.format(e.getClientInfo().getCpuUsage()) + "% | Started-Processes: " + (e.getClientInfo().getStartedProxies().size() + e.getClientInfo().getStartedServers().size()));
                    ReformCloudController.getInstance().getLoggerProvider().emptyLine();
                    commandSender.sendMessage("The following proxies are started on \"" + e.getName() + "\": ");
                    ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().getAllRegisteredProxyProcesses().stream().filter(proxyInfo -> proxyInfo.getCloudProcess().getClient().equals(e.getName())).forEach(info -> {
                        commandSender.sendMessage("    - " + info.getCloudProcess().getName() + " | Player=" + info.getOnline() + "/" + info.getProxyGroup().getMaxPlayers());
                    });
                    ReformCloudController.getInstance().getLoggerProvider().emptyLine();
                    commandSender.sendMessage("The following cloud-servers are started on \"" + e.getName() + "\": ");
                    ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().getAllRegisteredServerProcesses().stream().filter(serverInfo -> serverInfo.getCloudProcess().getClient().equals(e.getName())).forEach(info -> {
                        commandSender.sendMessage("    - " + info.getCloudProcess().getName() + " | Player=" + info.getOnline() + "/" + info.getServerGroup().getMaxPlayers());
                    });
                    ReformCloudController.getInstance().getLoggerProvider().emptyLine();
                });
                break;
            }
            case "start": {
                if (args.length == 2) {
                    if (ReformCloudController.getInstance().getInternalCloudNetwork().getServerGroups().containsKey(args[1])) {
                        final ServerGroup serverGroup = ReformCloudController.getInstance().getInternalCloudNetwork().getServerGroups().get(args[1]);
                        final Client client = ReformCloudController.getInstance().getBestClient(serverGroup.getClients(), serverGroup.getMemory());

                        if (client != null) {
                            final String id = ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().nextFreeServerID(serverGroup.getName());
                            final String name = serverGroup.getName() + ReformCloudController.getInstance().getCloudConfiguration().getSplitter() + (Integer.parseInt(id) <= 9 ? "0" : "") + id;
                            ReformCloudController.getInstance().getChannelHandler().sendPacketAsynchronous(client.getName(),
                                    new PacketOutStartGameServer(serverGroup, name, UUID.randomUUID(), new Configuration(), id)
                            );
                            commandSender.sendMessage("Trying to startup serverProcess...");
                        } else {
                            commandSender.sendMessage("The Client of the ServerGroup isn't connected to ReformCloudController or Client is not available to startup processes");
                        }
                    } else if (ReformCloudController.getInstance().getInternalCloudNetwork().getProxyGroups().containsKey(args[1])) {
                        final ProxyGroup proxyGroup = ReformCloudController.getInstance().getInternalCloudNetwork().getProxyGroups().get(args[1]);
                        final Client client = ReformCloudController.getInstance().getBestClient(proxyGroup.getClients(), proxyGroup.getMemory());

                        if (client != null) {
                            final String id = ReformCloudController.getInstance().getInternalCloudNetwork().getServerProcessManager().nextFreeProxyID(proxyGroup.getName());
                            final String name = proxyGroup.getName() + ReformCloudController.getInstance().getCloudConfiguration().getSplitter() + (Integer.parseInt(id) <= 9 ? "0" : "") + id;
                            ReformCloudController.getInstance().getChannelHandler().sendPacketAsynchronous(client.getName(),
                                    new PacketOutStartProxy(proxyGroup, name, UUID.randomUUID(), new Configuration(), id)
                            );
                            commandSender.sendMessage("Trying to startup proxyProcess...");
                        } else {
                            commandSender.sendMessage("The Client of the ProxyGroup isn't connected to ReformCloudController or Client is not available to startup processes");
                        }
                    } else {
                        commandSender.sendMessage("ServerGroup or ProxyGroup doesn't exists");
                    }
                } else {
                    commandSender.sendMessage("process start <groupName>");
                }
                break;
            }
            default: {
                commandSender.sendMessage("process stop <name>");
                commandSender.sendMessage("process start <group-name>");
                commandSender.sendMessage("process list");
            }
        }
    }

    @Override
    public String getPermission() {
        return "reformcloud.command.process";
    }
}
