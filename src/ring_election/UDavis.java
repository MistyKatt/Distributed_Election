package davis;

import davis.naming.IdScheme;
import davis.networks.NetworkGenerator;
import davis.nodes.StatefulNode;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jbotsim.MessageEngine;
import jbotsim.Node;
import jbotsim.Topology;
import jbotsim.ui.JTopology;
import jbotsimx.messaging.AsyncFaultyMessageEngine;
import jbotsimx.messaging.AsyncMessageEngine;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import davis.AsFar;
import davis.ControlDis;
import davis.Stage;
import davis.StageNofeedback;
import davis.Alternative;
import davis.DifSpeed;
public class UDavis extends JPanel {
   
    static JSlider slideBar = new JSlider(0, 800);
    static int size;
    static Topology topo;
    static boolean hasStarted;
    static Class topoc;
    static Class algoc;
    static Class schemec;
    static IdScheme scheme;
    static boolean async=false;
    static boolean fifo=true;
    static boolean lossy=false;
    static MessageEngine msgengine=null;
    
    static void reinit() {
        try {
            Class class_ = topo.getNodeModel("default");
           // int n = topo.getClockSpeed();
            topo = new Topology(false);
            topo.setNodeModel("default", class_);
            topo.setClockSpeed(1000);
            topo.setDimensions(700, 600);
            topo.resetTime();
            
            NetworkGenerator networkGenerator = (NetworkGenerator)topoc.newInstance();
            networkGenerator.networkSize = size;
            System.out.println(networkGenerator.networkSize);
            networkGenerator.generate(topo, scheme);
            topo.pause();
            
            topo.setMessageEngine(msgengine);

            topo.resume();
        }
        catch (Exception var0_1) {
            var0_1.printStackTrace();
        }
    }

    public static void main(String[] arrstring) throws ParseException {
        boolean bl = false;
        int n = 0;
        Options opt=new Options();
        opt.addOption("x", false, "Disable the graphic interface");
        opt.addOption("a",false,"Makes the system asynchronus, default is synchronous");
        opt.addOption("l",false,"Makes the channels lossy and asynch");
        opt.addOption("nf",false,"Disable the FIFO property on links, it only works in not synchronous environments");
        opt.addOption("h", false, "Shows the help");
        topoc= davis.networks.UnidirRing.class;
        schemec=davis.naming.RandomScheme.class;
        size=25;
        algoc=davis.QElection.class;
        
        
        String string = UDavis.class.getPackage().getImplementationVersion();
        CommandLineParser clp=new DefaultParser();
        CommandLine cmd=clp.parse(opt, arrstring);
                
       
        
     
        
        
        
        arrstring=cmd.getArgs();
        for(String x: arrstring){
            System.out.println(x);
        }
          if (cmd.hasOption("h") || arrstring.length == 0) {
            System.out.println("\u00b5davis (" + string + ")");
            System.out.println("  A micro viewer for JBotsim.\n");
            System.out.println("");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "Usage: udavis [Options] <network class> <id scheme> <size> <algo class>", opt );
           // return;
        }
        
          String string2 = arrstring[n + 0];
          String string3 = arrstring[n + 1];
          size = Integer.parseInt(arrstring[n + 2]);
          String string4 = arrstring[n + 3];
        /*
        
        if(string2 == null)
        	string2="davis.networks.BidirRing";
        else
        	string2 = "davis.networks."+string2;
       
        if(string3 == null)
            string3="davis.naming.RandomScheme";
        else
        	string3 = "davis.naming."+string3;
        size = 48;
        
        if(string4 == null)
            string4="davis.Stage";
        else
        	string4 = "davis."+string4;
*/
        
       
         int upperbound=size*10;
       
        if (cmd.hasOption("x") ) {
            bl = true;
        }
           if(cmd.hasOption("nf")){
            fifo=false;
        }
        if (false) {
            System.out.println("The system is asynchrnouns, upperbound:"+upperbound);
            async = true;
            msgengine=new AsyncMessageEngine(upperbound, (fifo)?AsyncMessageEngine.Type.FIFO:AsyncMessageEngine.Type.NONFIFO);
        }else
         if (cmd.hasOption("l") ) {
            lossy = true;
            msgengine=new AsyncFaultyMessageEngine(1,upperbound, (fifo)?AsyncMessageEngine.Type.FIFO:AsyncMessageEngine.Type.NONFIFO);
        }else{
            msgengine=new MessageEngine();
         }
        
        try {
            topoc = Class.forName(string2);
            schemec = Class.forName(string3);
            algoc = Class.forName(string4);
        }
        catch (Exception var7_7) {
            var7_7.printStackTrace();
        }
        try {
            scheme = (IdScheme)schemec.newInstance();
        }
        catch (Exception var7_8) {
            var7_8.printStackTrace();
        }
        try {
            topo = new Topology(false);
            topo.setNodeModel("default", algoc);
            topo.setMessageEngine(msgengine);
            if (!bl) {
                ((StatefulNode)topo.newInstanceOfModel("default")).setColorsMap();
            }

                

        }
        catch (Exception var7_10) {
            var7_10.printStackTrace();
        }
        if (bl) {
            UDavis.reinit();
            topo.restart();
            topo.start();
            topo.setClockSpeed(1);
            topo.resume();
            while (StatefulNode.getRunnings() != 0) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException var7_11) {
                    Thread.currentThread().interrupt();
                }
            }
            System.exit(0);
        }
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(3);
        jFrame.setTitle("\u00b5davis -- A micro viewer for JBotsim");
        jFrame.setSize(810, 660);
        jFrame.setLocationRelativeTo(null);
        UDavis uDavis = new UDavis(topoc, schemec, algoc);
        uDavis.setPreferredSize(new Dimension(800, 650));
        jFrame.add(uDavis);
        jFrame.setVisible(true);
    }

    UDavis(Class class2_, Class class_2, Class class_3) {
        slideBar.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                
            }
        });
        JPanel jPanel = new JPanel();
        JButton jButton = new JButton("* NEW *");
        JPanel jPanel2 = new JPanel();
        RenewAction renewAction2 = new RenewAction(jPanel2);
        jButton.addActionListener(renewAction2);
        jPanel.add(jButton);
        JButton jButton2 = new JButton("| Start >");
        jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (UDavis.hasStarted) {
                    UDavis.topo.resume();
                } else {
                    
                    UDavis.hasStarted = true;
                    UDavis.topo.restart();
                    UDavis.topo.start();
                   
                }
            }
        });
       
        jButton2.addActionListener(renewAction2);
        jPanel.add(jButton2);
        JButton jButton3 = new JButton("| Pause |");
        jButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UDavis.topo.pause();
            }
        });
        jPanel.add(jButton3);
        JButton jButton4 = new JButton("| Step |");
        jButton4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UDavis.topo.step();
            }
        });
        jPanel.add(jButton4);
        jPanel.add(slideBar);
        this.add((Component)jPanel, "North");
        this.add((Component)jPanel2, "Center");
        renewAction2.actionPerformed(null);
        JPanel jPanel3 = new JPanel();
        jPanel3.add(new JLabel("STATES:"));
        HashMap<String, Color> hashMap = null;
        try {
            StatefulNode statefulNode = (StatefulNode)((Object)class_3.newInstance());
            statefulNode.setColorsMap();
            hashMap = statefulNode.getColorsMap();
        }
        catch (Exception var13_14) {
            var13_14.printStackTrace();
        }
        for (String string : hashMap.keySet()) {
            jPanel3.add(new JLabel("  " + string + ":"));
            JLabel jLabel = new JLabel("     ");
            Color color = hashMap.get(string);
            try {
                jLabel.setBackground(color);
            }
            catch (Exception var17_18) {
                System.err.println("Color " + color + " is not supported.");
            }
            jLabel.setOpaque(true);
            jPanel3.add(jLabel);
        }
        this.add((Component)jPanel3, "South");
    }

    static {
        hasStarted = false;
        topoc = null;
        algoc = null;
        schemec = null;
        scheme = null;
    }



    static class RenewAction
    implements ActionListener {
        JPanel viewer;
        JTopology svgd = null;

        RenewAction(JPanel jPanel) {
            this.viewer = jPanel;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                UDavis.hasStarted = false;
                UDavis.reinit();
                JTopology jTopology = new JTopology(UDavis.topo);
                if (this.svgd != null) {
                    this.viewer.remove((Component)this.svgd);
                }
                this.svgd = jTopology;
                this.viewer.add((Component)jTopology);
                this.viewer.revalidate();
                this.viewer.repaint();
            }
            catch (Exception var2_3) {
                var2_3.printStackTrace();
            }
        }
    }

    
}

