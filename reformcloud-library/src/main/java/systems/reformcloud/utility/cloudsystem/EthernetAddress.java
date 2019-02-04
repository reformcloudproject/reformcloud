/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.utility.cloudsystem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author _Klaro | Pasqual K. / created on 21.10.2018
 */

@AllArgsConstructor
@Getter
public class EthernetAddress {
    private String host;
    private int port;
}
