package de.dakir.supportchat.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Data {

    public static ArrayList<UUID> supports = new ArrayList<>();
    public static HashMap<UUID, UUID> inSupport = new HashMap<>();

    public static String aliases = "";
    public static String aliasesString = "";
    public static boolean enableMySQL;
    public static boolean enableQueueNotification;
    public static int queueNotificationInterval;

}
