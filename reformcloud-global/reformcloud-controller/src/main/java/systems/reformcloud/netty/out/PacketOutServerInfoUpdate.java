/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.netty.out;

import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.meta.info.ServerInfo;
import systems.reformcloud.netty.packet.Packet;
import systems.reformcloud.netty.packet.enums.PacketSender;
import systems.reformcloud.netty.packet.enums.QueryType;
import systems.reformcloud.utility.cloudsystem.InternalCloudNetwork;

import java.util.Collections;

/**
 * @author _Klaro | Pasqual K. / created on 12.12.2018
 */

public final class PacketOutServerInfoUpdate extends Packet {
    public PacketOutServerInfoUpdate(final ServerInfo serverInfo, final InternalCloudNetwork internalCloudNetwork) {
        super("ServerInfoUpdate", new Configuration().addProperty("serverInfo", serverInfo).addProperty("networkProperties", internalCloudNetwork), Collections.singletonList(QueryType.COMPLETE), PacketSender.CONTROLLER);
    }
}
