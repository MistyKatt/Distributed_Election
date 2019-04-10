/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davis;

import davis.nodes.RingNode;

import static java.lang.Math.pow;
/**
 *
 * @author Lenovo
 */
public class ControlDis extends RingNode {
    long starttime,endtime,time;
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset
    Integer isnextlevel=0;//0,1->wait for next level 2 get to next level
    Integer istermi=0;// check message from opposite sides
    double levelnow=0;
    Integer ischanged=0;//dircetion of msg not change at first
    Integer [] message1={0,0,0,0,0};//uid dis maxdistance direction
    Integer [] message2={0,0,0,0,0};
    Integer [] termi={-1,0,0,0,0};
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
        message1[0]=uid;
        message1[1]=1;
        message1[2]=1;
        message1[3]=0;//send msg to the left
        message1[4]=0;
	sendLeft(message1);
        message2[0]=uid;
        message2[1]=1;
        message2[2]=1;
        message2[3]=1;//send msg to the left
        message2[4]=0;
        starttime=System.currentTimeMillis();
        sendRight(message2);// send right message
	become("active"); // initial state
    }

    public void active(Integer [] msg) {
	
       if (msg[0] > uid) { // not leader
	    log("Je ne suis PAS ELU...");
	    become("follower");
            if( msg[1]<msg[2])
            {   msg[1]++;
                if(msg[3]==0)
                     sendLeft(msg);
                else
                     sendRight(msg);
            
            }
            else
                if(msg[3]==0)
                    {
                        msg[3]=1;
                        sendRight(msg);
                        msg[4]=1;
                    }
                else
                    {
                        msg[3]=0;
                        sendLeft(msg);
                        msg[4]=1;
                    }
	}
        
	if (msg[0] == uid) { // own message
            
	    if(msg[4]==0)
            {istermi++;
            
                }
            else
            {   isnextlevel++;
            
            
            }
            if(istermi==2)
            {  
               
               become("elected") ;
               endtime=System.currentTimeMillis();
               System.out.println("time:"+Math.abs(starttime-endtime)+'\n');
               System.out.println("message number:"+super.getmessagenumber());  
             }
            if(isnextlevel==2)
            {   isnextlevel=0;
                levelnow++;
                
                message1[0]=uid;
                message1[1]=1;
                message1[2]=(int)pow(2.0,levelnow);
                
                message1[4]=0;
                message1[3]=0;//send msg to the left
                sendLeft(message1);
                message2[0]=uid;
                message2[1]=1;
                message2[2]=(int)pow(2.0,levelnow);
                
                message2[4]=0;
                message2[3]=1;//send msg to the left
               
                sendRight(message2);
               
            }
	      // local termination
	
	}	    
    }
    
    public void follower(Integer [] msg) {
	
        if(msg[0]<=uid)
            {}
            else
            {
            if(msg[4]==1)  
            {
                 if(msg[3]==0)
                     sendLeft(msg);
                 else
                     sendRight(msg);
            }
            else
            {
                    
          
            if(msg[1]<msg[2])
            {   msg[1]++;
                if(msg[3]==0)
                     sendLeft(msg);
                else
                     sendRight(msg);
            
            }
            else  {
                if(msg[3]==0)
                    {
                        msg[3]=1;
                        sendRight(msg);
                        msg[4]=1;
                    }
                else
                    {
                        msg[3]=0;
                        sendLeft(msg);
                        msg[4]=1;
                    }
            }
            }        
            }
            
            
		    
    }

    public void elected(Integer[] msg){
          if (msg[0]==-1)
          {  terminate();
             terminated=true;
             System.out.println(round);        
          }
    }

} 
