/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Link
 *  jbotsim.Message
 *  jbotsim.Node
 */
package davis.nodes;

import java.awt.Color;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import jbotsim.Link;
import jbotsim.Message;
import jbotsim.Node;

public abstract class StatefulNode
extends Node {
    static HashMap<String, Integer> knowledge = new HashMap<>();
    String state;
    static HashMap<String, HashMap<Class, Method>> actionsMap = new HashMap<>();
    static HashMap<String, Color> colorsMap = new HashMap<>();
    String[] actions;
    public static int totalMessages;
    static int runnings;
    boolean hasPorts = false;
    boolean hasReallyStarted = false;

    StatefulNode() {
        ++runnings;
        Method[] arrmethod = this.getClass().getDeclaredMethods();
        this.actions = new String[arrmethod.length];
        for (int i = 0; i < arrmethod.length; ++i) {
            Class class_;
            String string = arrmethod[i].getName();
            Class<?>[] arrclass = arrmethod[i].getParameterTypes();
            if (arrclass.length == 0) continue;
            if (arrclass.length == 1) {
                class_ = arrclass[0];
            } else {
                this.hasPorts = true;
                class_ = arrclass[1];
            }
            if (actionsMap.get(string) == null) {
                actionsMap.put(string, new HashMap());
            }
            actionsMap.get(string).put(class_, arrmethod[i]);
            this.actions[i] = string;
        }
    }

    public static int getRunnings() {
        return runnings;
    }

    public static void resetRunnings() {
        runnings = 0;
    }

    public void setKnowledge(String string, int n) {
        knowledge.put(string, n);
    }

    public int getKnowledge(String string) {
        return knowledge.get(string);
    }

    public void setColorsMap() {
        ArrayList arrayList = new ArrayList(StatefulNode.getBasicColors());
        Collections.shuffle(arrayList);
        for (int i = 0; i < this.actions.length; ++i) {
            Color color = (Color)arrayList.get(i % arrayList.size());
            if (this.actions[i] == null) continue;
            colorsMap.put(this.actions[i], color);
        }
    }

    public void setStateColor(String string, Color color) {
        if (actionsMap.containsKey(string)) {
            colorsMap.put(string, color);
        }
    }

    public HashMap<String, Color> getColorsMap() {
        return colorsMap;
    }

    public String getFullState() {
        return this.state;
    }

    public String toString() {
        return "" + this.getID() + " (" + this.getFullState() + ")";
    }

    public void log(String string) {
        //System.out.println("LOG " + (Object)((Object)this) + ":\t" + string);
    }
    public int getmessagenumber()
    {
        return totalMessages;
    }
    public abstract void init();

    public void onStart() {
        if (!this.hasReallyStarted) {
            //this.log("VOID START");
            this.hasReallyStarted = true;
        } else {
           // System.out.println("START " + (Object)((Object)this));
            this.init();
            this.setState((Object)((Object)this));
        }
    }

    public void onMessage(Message message) {
        try {
            System.out.println("RECV   " + ++totalMessages + "\t" + (Object)message);
            if (this.state != "terminated") {
                if (!this.hasPorts) {
                    actionsMap.get(this.state).get(message.getContent().getClass()).invoke((Object)((Object)this), message.getContent());
                } else {
                    actionsMap.get(this.state).get(message.getContent().getClass()).invoke((Object)((Object)this), new Object[]{message.getSender(), message.getContent()});
                }
                this.setState((Object)((Object)this));
            }
        }
        catch (Exception var2_2) {
            var2_2.printStackTrace();
        }
    }

    public void become(String string) {
       // System.out.println("BECOME " + this.getID() + " " + this.state + "->" + string);
        this.state = string;
        if (this.state != "terminated") {
            this.setColor(colorsMap.get(this.state));
        } else {
            --runnings;
        }
    }

    public final void terminate() {
        this.become("terminated");
    }

    public String getColorAsString() {
        return this.getColor().toString();
    }

    public void select(StatefulNode statefulNode) {
        this.select(statefulNode, Color.red);
    }

    public void select(StatefulNode statefulNode, Color color) {
        this.getCommonLinkWith((Node)statefulNode).setWidth(Integer.valueOf(3));
        this.getCommonLinkWith((Node)statefulNode).setColor(color);
    }

    public void unselect(StatefulNode statefulNode) {
        this.getCommonLinkWith((Node)statefulNode).setWidth(Integer.valueOf(1));
        this.getCommonLinkWith((Node)statefulNode).setColor(Color.black);
    }

    static {
        runnings = 0;
    }
}

