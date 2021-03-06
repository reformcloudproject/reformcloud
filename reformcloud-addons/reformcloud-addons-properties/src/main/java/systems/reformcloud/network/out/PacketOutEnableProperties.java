/*
  Copyright © 2019 Pasqual K. | All rights reserved
 */

package systems.reformcloud.network.out;

import java.io.Serializable;
import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.network.packet.Packet;

/**
 * @author _Klaro | Pasqual K. / created on 22.04.2019
 */

public final class PacketOutEnableProperties extends Packet implements Serializable {

    public PacketOutEnableProperties() {
        super("EnableProperties", new Configuration());
    }
}
