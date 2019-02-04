/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.netty.packets;

import systems.reformcloud.ReformCloudAPIBungee;
import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.netty.packet.Packet;
import systems.reformcloud.netty.packet.enums.PacketSender;
import systems.reformcloud.netty.packet.enums.QueryType;

import java.util.Collections;
import java.util.UUID;

/**
 * @author _Klaro | Pasqual K. / created on 07.11.2018
 */

public final class PacketOutCommandExecute extends Packet {
    public PacketOutCommandExecute(final String name, final UUID uuid, final String command) {
        super("CommandExecute", new Configuration().addProperty("uuid", uuid).addStringProperty("command", command).addStringProperty("name", name).addStringProperty("proxyName", ReformCloudAPIBungee.getInstance().getProxyInfo().getCloudProcess().getName()), Collections.singletonList(QueryType.COMPLETE), PacketSender.PROCESS_PROXY);
    }
}
