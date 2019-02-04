/*
  Copyright © 2019 Pasqual K. | All rights reserved
 */

package systems.reformcloud.netty.packets.out;

import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.netty.packet.Packet;
import systems.reformcloud.netty.packet.enums.PacketSender;
import systems.reformcloud.netty.packet.enums.QueryType;

import java.io.Serializable;
import java.util.Collections;

/**
 * @author _Klaro | Pasqual K. / created on 29.01.2019
 */

public final class PacketOutGetLog extends Packet implements Serializable {
    private static final long serialVersionUID = 7160830277462211122L;

    public PacketOutGetLog(final String url, final String process) {
        super(
                "ProcessLog",
                new Configuration().addStringProperty("process", process).addStringProperty("url", url),
                Collections.singletonList(QueryType.COMPLETE),
                PacketSender.CLIENT
        );
    }
}
