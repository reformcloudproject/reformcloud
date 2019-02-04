/*
  Copyright © 2018 Pasqual K. | All rights reserved
 */

package systems.reformcloud.configuration;

import systems.reformcloud.ReformCloudClient;
import systems.reformcloud.ReformCloudLibraryService;
import systems.reformcloud.ReformCloudLibraryServiceProvider;
import systems.reformcloud.logging.LoggerProvider;
import systems.reformcloud.utility.StringUtil;
import systems.reformcloud.utility.checkable.Checkable;
import systems.reformcloud.utility.cloudsystem.EthernetAddress;
import systems.reformcloud.utility.files.FileUtils;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author _Klaro | Pasqual K. / created on 24.10.2018
 */

@Getter
public class CloudConfiguration {
    private String controllerKey, controllerIP, clientName, startIP;
    private int memory, controllerPort, controllerWebPort;
    private double cpu;

    private EthernetAddress ethernetAddress;

    /**
     * Creates or/and loads the Client Configuration
     *
     * @throws Throwable
     */
    public CloudConfiguration() {
        if (!Files.exists(Paths.get("configuration.properties")) && !Files.exists(Paths.get("ControllerKEY"))) {
            ReformCloudClient.getInstance().getLoggerProvider().err("Please copy the \"ControllerKEY\" file in the root directory of the client");
            ReformCloudLibraryService.sleep(2000);
            System.exit(-1);
            return;
        } else if (Files.exists(Paths.get("ControllerKEY"))) {
            this.createDirectories();
            FileUtils.copyFile("ControllerKEY", "reformcloud/files/ControllerKEY");
            FileUtils.deleteFileIfExists(Paths.get("ControllerKEY"));
        }

        this.clearProxyTemp();
        this.clearServerTemp();

        this.defaultInit();
        this.load();
    }

    private void createDirectories() {
        for (File dir : new File[]{
                new File("reformcloud/templates"),
                new File("reformcloud/default/proxies/plugins"),
                new File("reformcloud/default/servers/plugins"),
                new File("reformcloud/temp/servers"),
                new File("reformcloud/temp/proxies"),
                new File("reformcloud/files")
        })
            dir.mkdirs();
    }

    /**
     * Creates default Configuration or
     * returns if the Configuration already exists
     *
     * @return
     * @throws Throwable
     */
    private boolean defaultInit() {
        if (Files.exists(Paths.get("configuration.properties")))
            return false;

        LoggerProvider loggerProvider = ReformCloudClient.getInstance().getLoggerProvider();

        loggerProvider.info("Please provide the internal ReformCloudClient ip");
        String ip = this.readString(loggerProvider, s -> s.split("\\.").length == 4);
        loggerProvider.info("Please provide the Controller IP");
        String controllerIP = this.readString(loggerProvider, s -> s.split("\\.").length == 4);

        ReformCloudClient.getInstance().getLoggerProvider().info("Please provide the name of this Client. (Recommended: Client-01)");
        String clientID = this.readString(loggerProvider, s -> true);

        Properties properties = new Properties();

        properties.setProperty("controller.ip", controllerIP);
        properties.setProperty("controller.port", 5000 + StringUtil.EMPTY);
        properties.setProperty("controller.web-port", 4790 + StringUtil.EMPTY);
        properties.setProperty("general.client-name", clientID);
        properties.setProperty("general.start-host", ip);
        properties.setProperty("general.memory", 1024 + StringUtil.EMPTY);
        properties.setProperty("general.maxcpuusage", 90.00 + StringUtil.EMPTY);

        try (OutputStream outputStream = Files.newOutputStream(Paths.get("configuration.properties"))) {
            properties.store(outputStream, "ReformCloud default Configuration");
        } catch (final IOException ex) {
            StringUtil.printError(ReformCloudLibraryServiceProvider.getInstance().getLoggerProvider(), "Could not store configuration.properties", ex);
            return false;
        }

        return true;
    }

    public void clearServerTemp() {
        final File dir = new File("reformcloud/temp/servers");

        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    FileUtils.deleteFullDirectory(file.toPath());
                }
            }
        }
    }

    public void clearProxyTemp() {
        final File dir = new File("reformcloud/temp/proxies");

        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    FileUtils.deleteFullDirectory(file.toPath());
                }
            }
        }
    }

    /**
     * Loads the CloudConfiguration
     */
    private void load() {
        Properties properties = new Properties();
        try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(Paths.get("configuration.properties")))) {
            properties.load(inputStreamReader);
        } catch (final IOException ex) {
            StringUtil.printError(ReformCloudLibraryServiceProvider.getInstance().getLoggerProvider(), "Could not load configuration.properties", ex);
        }

        this.clientName = properties.getProperty("general.client-name");
        this.startIP = properties.getProperty("general.start-host");
        this.memory = Integer.parseInt(properties.getProperty("general.memory"));

        this.controllerIP = properties.getProperty("controller.ip");
        this.controllerPort = Integer.parseInt(properties.getProperty("controller.port"));
        this.controllerWebPort = Integer.parseInt(properties.getProperty("controller.web-port"));
        this.cpu = Double.parseDouble(properties.getProperty("general.maxcpuusage"));

        this.ethernetAddress = new EthernetAddress(this.controllerIP, this.controllerPort);

        this.controllerKey = FileUtils.readFileAsString(new File("reformcloud/files/ControllerKEY"));
    }

    private String readString(final LoggerProvider loggerProvider, Checkable<String> checkable) {
        String readLine = loggerProvider.readLine();
        while (readLine == null || !checkable.isChecked(readLine) || readLine.trim().isEmpty()) {
            loggerProvider.info("Input invalid, please try again");
            readLine = loggerProvider.readLine();
        }

        return readLine;
    }

    private Integer readInt(final LoggerProvider loggerProvider, Checkable<Integer> checkable) {
        String readLine = loggerProvider.readLine();
        while (readLine == null || readLine.trim().isEmpty() || !ReformCloudLibraryService.checkIsInteger(readLine) || !checkable.isChecked(Integer.parseInt(readLine))) {
            loggerProvider.info("Input invalid, please try again");
            readLine = loggerProvider.readLine();
        }

        return Integer.parseInt(readLine);
    }
}
