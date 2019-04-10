/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Lenovo
 */
package davis;

import davis.nodes.RingNode;
import static java.lang.Math.pow;
import java.util.Objects;

public class DifSpeed extends RingNode {
    long starttime,endtime;
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset
    Integer [] message={0,0,0};
    Integer [] receivedmsg={-1,0,-1};
    Integer round=0;
    Boolean terminated=false;
 
    public void onClock()
    {
       if(!terminated){ 
           round++;
           message[1]++;
          
          
       }
       else
           return;
        if(Objects.equals(message[1], message[2]))
        {
           
            sendRight(message); // send own id
            terminated=true;
           
          
          // initial state
        }
         
    }

    public void init(){
	uid = getID(); // init id
	leader = uid;
        message[0]=uid;
        message[1]=0;
        message[2]=(int)pow(2.0,uid);
        starttime=System.currentTimeMillis();
       
        become("active"); // initial state
        
    }

    public void active(Integer[] msg) {
        if(msg[0]==-1)
        {terminate();
            sendRight(receivedmsg);
            terminated=true;
        }
        else
        {
        if(msg[0]!=uid)
        terminated=false;
	 if(msg[0]==uid)
        {   become("elected");
            sendRight(receivedmsg);
        terminated=true;
        endtime=System.currentTimeMillis();
        System.out.println("time:"+Math.abs(starttime-endtime)+'\n');
        System.out.println("message number:"+super.getmessagenumber());  
        
        
        }
         
        message=msg;
        message[1]=0;
        }
       
        
    }
    
   

    public void elected(Integer[] msg){
        if(msg[0]==-1)
        {terminate();}
    }

}
