/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Message
 */
package davis.nodes;

import davis.nodes.StatefulNode;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import jbotsim.Message;

public abstract class PlaceNode<B>
extends StatefulNode {
    B board;

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("RECV   " + ++totalMessages + "\t" + (Object)message);
            if (this.state != "terminated") {
                Agent agent = (Agent)((Object)message.getContent());
                ((Method)((HashMap)Agent.actionsMap.get(this.state)).values().iterator().next()).invoke((Object)agent, this.board);
                this.setState((Object)this);
            }
        }
        catch (Exception var2_3) {
            var2_3.printStackTrace();
        }
    }
}

