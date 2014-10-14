// JTwain.java

package net.javajeff.jtwain;

import java.awt.Image;

/**
 *  This class provides the "glue" to connect the Java side of JTWAIN to the
 *  C++ side. Methods exist to initialize JTwain and interact with TWAIN.
 *
 *  @author Jeff Friesen
 */

public class JTwain
{
   /**
    *  Initialize JTwain. Initialization succeeds if System.loadLibrary() is
    *  able to find the jtwain library, if the jtwain library is able to find
    *  TWAIN_32.DLL, and if TWAIN_32.DLL contains the DSM_Entry() function. A
    *  messagebox is displayed if either TWAIN_32.DLL or DSM_Entry() can't be
    *  found.
    *
    *  IMPORTANT: This method must be called before any other method. If this
    *  method returns false, do NOT call any other method.
    *
    *  @return true if JTwain successfully initialized, otherwise false
    */

   public static boolean init ()
   {
      try
      {
          System.loadLibrary ("jtwain");
          return true;
      }
      catch (UnsatisfiedLinkError e)
      { 
          return false;
      }
   }

   /**
    *  Display the default source's dialog box to let the user configure that
    *  source. If the user clicks the Scan button, acquire one image from the
    *  default source.
    *
    *  @return Image that describes the acquired image, otherwise null if the 
    *  user clicked the Cancel button on the default source's dialog box
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native Image acquire () throws JTwainException;

   /**
    *  Select a source name from the source manager's dialog box. If the user
    *  clicks the Ok button, the highlighted source name becomes the new
    *  default source.
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native void selectSourceAsDefault () throws JTwainException;
}
