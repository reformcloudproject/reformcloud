/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.meta.proxy;

import systems.reformcloud.meta.Template;
import systems.reformcloud.meta.enums.TemplateBackend;
import systems.reformcloud.meta.proxy.versions.ProxyVersions;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author _Klaro | Pasqual K. / created on 21.10.2018
 */

@AllArgsConstructor
@Data
public class ProxyGroup implements Serializable {
    private static final long serialVersionUID = 4196459006374952552L;

    protected String name;

    protected List<String> clients, disabledServerGroups;
    protected List<Template> templates;
    protected Collection<UUID> whitelist;

    protected boolean controllerCommandLogging, maintenance;

    protected int startPort, minOnline, maxOnline, maxPlayers, memory;

    protected ProxyVersions proxyVersions;

    public Template getTemplate(final String name) {
        return this.templates.stream().filter(template -> template.getName().equals(name)).findFirst().orElse(randomTemplate());
    }

    public Template randomTemplate() {
        if (this.templates.size() == 0) {
            return new Template("default", null, TemplateBackend.CLIENT);
        }

        return this.templates.get(new Random().nextInt(this.templates.size()));
    }
}
