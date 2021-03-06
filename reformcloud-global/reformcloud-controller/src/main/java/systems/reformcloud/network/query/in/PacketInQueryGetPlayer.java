/*
  Copyright © 2019 Pasqual K. | All rights reserved
 */

package systems.reformcloud.network.query.in;

import systems.reformcloud.ReformCloudController;
import systems.reformcloud.configurations.Configuration;
import systems.reformcloud.network.interfaces.NetworkQueryInboundHandler;
import systems.reformcloud.network.query.out.PacketOutQueryPlayerResult;
import systems.reformcloud.player.DefaultPlayer;
import systems.reformcloud.player.implementations.OfflinePlayer;
import systems.reformcloud.utility.TypeTokenAdaptor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author _Klaro | Pasqual K. / created on 22.02.2019
 */

public final class PacketInQueryGetPlayer implements Serializable, NetworkQueryInboundHandler {

    @Override
    public void handle(Configuration configuration, UUID resultID) {
        if (configuration.contains("uuid")) {
            OfflinePlayer offlinePlayer = ReformCloudController.getInstance().getPlayerDatabase()
                .getOfflinePlayer(
                    configuration.getValue("uuid", UUID.class)
                );

            ReformCloudController.getInstance().getChannelHandler().sendPacketSynchronized(
                configuration.getStringValue("from"), new PacketOutQueryPlayerResult(
                    offlinePlayer, resultID
                )
            );
        } else if (configuration.contains("player")) {
            DefaultPlayer defaultPlayer = configuration
                .getValue("player", TypeTokenAdaptor.getDEFAULT_PLAYER_TYPE());
            OfflinePlayer offlinePlayer = ReformCloudController.getInstance().getPlayerDatabase()
                .getOfflinePlayer(defaultPlayer);

            ReformCloudController.getInstance().getChannelHandler().sendPacketSynchronized(
                configuration.getStringValue("from"), new PacketOutQueryPlayerResult(
                    offlinePlayer, resultID
                )
            );
        } else {
            UUID uuid = ReformCloudController.getInstance().getPlayerDatabase()
                .getFromName(configuration.getStringValue("name"));
            if (uuid == null) {
                return;
            }

            OfflinePlayer offlinePlayer = ReformCloudController.getInstance().getPlayerDatabase()
                .getOfflinePlayer(uuid);

            ReformCloudController.getInstance().getChannelHandler().sendPacketSynchronized(
                configuration.getStringValue("from"), new PacketOutQueryPlayerResult(
                    offlinePlayer, resultID
                )
            );
        }
    }
}
