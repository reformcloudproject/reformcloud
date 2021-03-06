/*
  Copyright © 2019 Pasqual K. | All rights reserved
 */

package systems.reformcloud.packets.out;

import java.io.Serializable;
import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.mobs.SelectorMob;
import systems.reformcloud.network.packet.Packet;

/**
 * @author _Klaro | Pasqual K. / created on 21.04.2019
 */

public final class PacketOutDeleteMob extends Packet implements Serializable {

    public PacketOutDeleteMob(SelectorMob selectorMob) {
        super(
            "DeleteMob",
            new Configuration().addValue("mob", selectorMob)
        );
    }
}
