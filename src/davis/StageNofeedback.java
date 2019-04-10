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
public class StageNofeedback extends RingNode {
    long starttime,endtime;
    Integer leader = 0; // uid of the leader
    Integer uid = 0; // unset
    Integer getleft=0;//0,1->wait for next level 2 get to next level
    Integer getright=0;
    Integer nodelevel=0;
    private class Stack
    { Integer []  message;
      Stack pre;
      Stack next;   
    }
    Stack headleft=null;
    Stack tailleft=null;
    Stack templeft=null;
    Stack headright=null;
    Stack tailright=null;
    Stack tempright=null;
    Integer [] message1={0,0,0,0};
    Integer [] message2={0,0,0,0};
    Integer [] left;
    Integer [] right;
    Integer [] termi={-1,0,0,0};
    Integer round=0;
    boolean terminated=false;
    @Override
    public void onClock()
    {  
       if(!terminated){ 
           round++;
       }
       if(headleft==null||headright==null)
       {  
          
           return;
       }
       if(headleft.message[2]==nodelevel)
           getleft=1;
       if(headright.message[2]==nodelevel)
           getright=1;
       if(getleft==1&&getright==1)
       {   
           getleft=0;
           getright=0;
           if(headleft.next==null)
          {
              left=headleft.message;
              headleft=null;
              templeft=null;
              tailleft=null;
          }
          else
          {
              left=headleft.message;
              templeft=headleft.next;
              headleft.next.pre=null;
              headleft.next=null;
              headleft=templeft;
          }
          if(headright.next==null)
          {
              right=headright.message;
              headright=null;
              tempright=null;
              tailright=null;
          }
          else
          {
              right=headright.message;
              tempright=headright.next;
              headright.next.pre=null;
              headright.next=null;
              headright=tempright;
          }
      } 
      else
       {
          
          return ;
       }
      if(left[0]==uid&&right[0]==uid)
          
           {
               endtime=System.currentTimeMillis();
               System.out.println(starttime-endtime);
               become("elected");
               terminated=true;
               return;
           }
           
       if(left[0]<uid&&right[0]<uid)
       {     Integer [] messagel={0,0,0,0};
             Integer [] messager={0,0,0,0};
             nodelevel=nodelevel+1;
             messagel[0]=uid;
             messagel[1]=0;
             messagel[2]=nodelevel;
             messagel[3]=0;  
             sendLeft(messagel);
             messager[0]=uid;
             messager[1]=1;
             messager[2]=nodelevel;
             messager[3]=0;  
             sendRight(messager); 
       }
       else
       {
           become("follower");
       }
       
          
       
    }
    // Initialization method (MANDATORY)
    public void init(){
	uid = getID(); // init id
	leader = uid;
        message1[0]=uid;
        message1[1]=0;
        message1[2]=0;
        message1[3]=0;
	sendLeft(message1);
        message2[0]=uid;
        message2[1]=1;
        message2[2]=0;
        message2[3]=0;
	sendRight(message2);
        starttime=System.currentTimeMillis();
	become("active"); // initial state
    }

    public void active(Integer [] msg) {
      if(msg[1]==0) 
      {
          
          if(headleft==null)
          {   headleft=new Stack();
              headleft.message=msg;
              templeft=headleft;
              tailleft=headleft;
             
          }
          else
          {   Integer [] tempmsg;
              templeft=new Stack();
              templeft.message=msg;
              tailleft.next=templeft;
              templeft.pre=tailleft;
              tailleft=templeft;
              while(templeft.message[2]<templeft.pre.message[2]&&templeft.pre!=null)
              {  tempmsg=templeft.message;
                  templeft.message=templeft.pre.message;
                  templeft.pre.message=tempmsg;
                  
              }
          }
           
          
           
      }
      
      if(msg[1]==1)
      {
          
          if(headright==null)
             
          {   headright=new Stack();
              headright.message=msg;
              tempright=headright;
              tailright=headright;
              // System.out.println(headright.message[0]);
          }
          else
          {   Integer [] tempmsg;
              tempright=new Stack();
              tempright.message=msg;
              tailright.next=tempright;
              tempright.pre=tailright;
              tailright=tempright;
               while(tempright.message[2]<tempright.pre.message[2]&&tempright.pre!=null)
              {   tempmsg=tempright.message;
                  tempright.message=tempright.pre.message;
                  tempright.pre.message=tempmsg;
                  
              }
          }
         
      }
         
      //  System.out.println(uid);
    }
    
    public void follower(Integer [] msg) {
	
       
             if(msg[1]==0)
               sendLeft(msg);  
             else
               sendRight(msg);        
            	    
    }

    public void elected(Integer[] msg){
          if (msg[0]==-1)
          {  terminate();
             terminated=true;
             System.out.println(round);
           
          }
    }

}