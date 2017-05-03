package javagames.util;


import java.awt.event.*;

public class KeyboardInput implements KeyListener {
   
   private boolean[] keys;
   private int[] polled;
   private boolean[] released;
   
   public KeyboardInput() {
      keys = new boolean[ 256 ];
      polled = new int[ 256 ];
      released = new boolean[ 256 ]; 
   }

   public boolean keyDown( int keyCode ) {
      return polled[ keyCode ] > 0;
   }
   
   public boolean keyDownOnce( int keyCode ) {
      return polled[ keyCode ] == 1;
   }
   
   public boolean keysDown(int... keyCodes)
   {
	   for(int i = 0; i < keyCodes.length; i++)
	   {
		   if(polled[keyCodes[i]] > 0)
			   return true;
	   }
	   
	   return false;
   }
   
   public boolean keysDownOnce(int... keyCodes)
   {
	   for(int i = 0; i < keyCodes.length; i++)
	   {
		   if(polled[keyCodes[i]] == 1)
			   return true;
	   }
	   
	   return false;
   }
   
   public boolean keysReleased(int... keyCodes)
   {
	   for(int i = 0; i < keyCodes.length; i++)
	   {
		   if(released[keyCodes[i]])
			   return true;
	   }
	   
	   return false;
   }
   
   public synchronized void poll() {
      for( int i = 0; i < keys.length; ++i ) {
         if( keys[i] ) {
            polled[i]++;
         } 
         else if(polled[i] > 0)
         {
            polled[i] = 0;
            released[i] = true;
         }
         else
         {
        	 released[i] = false;
         }
      }
   }

   public synchronized void keyPressed( KeyEvent e ) {
      int keyCode = e.getKeyCode();
      if( keyCode >= 0 && keyCode < keys.length ) {
         keys[ keyCode ] = true;
      }
   }

   public synchronized void keyReleased( KeyEvent e ) {
      int keyCode = e.getKeyCode();
      if( keyCode >= 0 && keyCode < keys.length ) {
         keys[ keyCode ] = false;
      }
   }

   public void keyTyped( KeyEvent e ) {
      // Not needed
   }
}
