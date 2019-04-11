/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davis;

import davis.nodes.RingNode;

/**
 *
 * @author Lenovo
 */
public class AsFar extends RingNode {
    long time,starttime,endtime;
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset
    Integer [] message={0,0};
    Integer [] termi={-1,1};
    Integer round=0;
    boolean terminated=false;
    
    @Override
    public void onClock()
    {
       if(!terminated){ 
           round++;
          
       }
     
       
    }
    // Initialization method (MANDATORY)
    public void init(){
	uid = getID(); // init id
	leader = uid;
        message[0]=uid;
        starttime=System.currentTimeMillis();
        if (uid % 2==0)
        {   message[1]=1; // 1 is right
	    sendLeft(message);    
        }
        else
            sendRight(message);// send own id
	become("active"); // initial state
    }

    public void active(Integer [] msg) {
	if (msg[0] > uid) { // not leader
	    log("Je ne suis PAS ELU...");
	    become("follower");
            if( msg[1]!=1)
                sendRight(msg);
            else
                sendLeft(msg);
	}
	if (msg[0] == uid) { // own message
	    log("Je suis ELU!");
	    become("elected");
            endtime=System.currentTimeMillis();
            time=starttime-endtime;
            System.out.println("time:"+Math.abs(time)+'\n');
            System.out.println("message number:"+super.getmessagenumber());  
	}	    
    }
    
    public void follower(Integer [] msg) {
	if (msg[0] > uid) { // not leader
	  
            if( msg[1]!=1)
                sendRight(msg);
            else
                sendLeft(msg);
	}
        if (msg[0]==-1)
            sendRight(msg);
        
		    
    }

    public void elected(Integer[] msg){
       
          if (msg[0]==-1)
          {  terminate();
            
             terminated=true;
             
           
          }
    }

} 

