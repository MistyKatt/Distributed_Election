/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Message
 *  jbotsim.Node
 */
package davis.nodes;

import davis.nodes.StatefulNode;
import java.awt.Color;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import jbotsim.Message;
import jbotsim.Node;

public abstract class PortNode
extends StatefulNode {
    public int degree;
    PortNode[] neighbors = null;
    List<PortNode> neighborsList = null;

    public PortNode() {
        ++runnings;
        Method[] arrmethod = this.getClass().getDeclaredMethods();
        this.actions = new String[arrmethod.length];
        for (int i = 0; i < arrmethod.length; ++i) {
            String string = arrmethod[i].getName();
            Class<?>[] arrclass = arrmethod[i].getParameterTypes();
            if (arrclass.length == 0) continue;
            Class class_ = arrclass.length == 1 ? arrclass[0] : arrclass[1];
            if (actionsMap.get(string) == null) {
                actionsMap.put(string, new HashMap());
            }
            ((HashMap)actionsMap.get(string)).put(class_, arrmethod[i]);
            this.actions[i] = string;
        }
    }

    public void initPortNumbers() {
        List list = this.getNeighbors();
        this.degree = list.size();
        this.neighbors = new PortNode[this.degree];
        for (int i = 0; i < this.degree; ++i) {
            this.neighbors[i] = (PortNode)((Object)list.get(i));
        }
        this.neighborsList = Arrays.asList(this.neighbors);
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("RECV   " + ++totalMessages + "\t" + (Object)message);
            if (this.state != "terminated") {
                ((Method)((HashMap)actionsMap.get(this.state)).get(message.getContent().getClass())).invoke((Object)this, this.neighborsList.indexOf((Object)message.getSender()), message.getContent());
                this.setState((Object)this);
            }
        }
        catch (Exception var2_2) {
            var2_2.printStackTrace();
        }
    }

    public void send(int n, Object object) {
        if (this.neighbors != null && n < this.degree) {
            this.send((Node)this.neighbors[n], object);
        }
    }

    public void select(int n) {
        this.select(n, Color.red);
    }

    public void select(int n, Color color) {
        if (this.neighbors != null && n < this.degree) {
            this.select(this.neighbors[n], color);
            this.log("SELECT LINK TO " + (Object)((Object)this.neighbors[n]));
        } else {
            this.log("WARNING Invalid port number :" + n);
        }
    }

    public void unselect(int n) {
        if (this.neighbors != null && n < this.degree) {
            this.unselect(this.neighbors[n]);
            this.log("UNSELECT LINK TO " + (Object)((Object)this.neighbors[n]));
        } else {
            this.log("WARNING Invalid port number :" + n);
        }
    }
    
}

