/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  jbotsim.Link
 *  jbotsim.Node
 *  jbotsim.Topology
 */
package davis;

import java.util.List;
import jbotsim.Link;
import jbotsim.Node;
import jbotsim.Topology;

class Tikz {
    Tikz() {
    }

    public static String exportTopology(Topology topology) {
        return Tikz.exportTopology(topology, 50);
    }

    public static String exportTopology(Topology topology, int n) {
        String string = "\n";
        String string2 = "\\begin{tikzpicture}[xscale=1,yscale=1]" + string;
        Integer n2 = (int)topology.getSensingRange();
        if (n2 != 0) {
            string2 = string2 + "  \\tikzstyle{every node}=[draw,circle,inner sep=" + (double)n2.intValue() / 5.0 + ", fill opacity=0.5,gray,fill=gray!40]" + string;
            for (Node node2 : topology.getNodes()) {
                double d = (double)Math.round(node2.getX() * 100.0 / (double)n) / 100.0;
                double d2 = (double)Math.round((600.0 - node2.getY()) * 100.0 / (double)n) / 100.0;
                string2 = string2 + "  \\path (" + d + "," + d2 + ") node (v" + (Object)node2 + ") {};" + string;
            }
        }
        string2 = string2 + "  \\tikzstyle{every node}=[draw,circle,fill=gray,inner sep=1.5]" + string;
        for (Node node2 : topology.getNodes()) {
            String string3 = "v" + node2.toString();
            double d = (double)Math.round(node2.getX() * 100.0 / (double)n) / 100.0;
            double d3 = (double)Math.round((600.0 - node2.getY()) * 100.0 / (double)n) / 100.0;
            string2 = string2 + "  \\path (" + d + "," + d3 + ") node (" + string3 + ") {};" + string;
        }
        string2 = string2 + "  \\tikzstyle{every path}=[];" + string;
        for (Link node2 : topology.getLinks()) {
            String string4 = "";
            if (node2.getWidth() > 1) {
                string4 = "[ultra thick]";
            }
            String string5 = "v" + node2.source.toString();
            String string6 = "v" + node2.destination.toString();
            string2 = string2 + "  \\draw" + string4 + " (" + string5 + ")--(" + string6 + ");" + string;
        }
        string2 = string2 + "\\end{tikzpicture}" + string;
        return string2;
    }
}

