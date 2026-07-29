package net.minecraft.client.renderer;

import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GLContext;

public class OpenGlHelper {

   public static int defaultTexUnit;
   public static int lightmapTexUnit;
   private static boolean useMultitextureARB;

   public static void initializeTextures() {
      useMultitextureARB = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;
      if(useMultitextureARB) {
         defaultTexUnit = '\u84c0';
         lightmapTexUnit = '\u84c1';
      } else {
         defaultTexUnit = '\u84c0';
         lightmapTexUnit = '\u84c1';
      }

   }

   public static void setActiveTexture(int par0) {
      if(useMultitextureARB) {
         ARBMultitexture.glActiveTextureARB(par0);
      } else {
         GL13.glActiveTexture(par0);
      }

   }

   public static void setClientActiveTexture(int par0) {
      if(useMultitextureARB) {
         ARBMultitexture.glClientActiveTextureARB(par0);
      } else {
         GL13.glClientActiveTexture(par0);
      }

   }

   public static void setLightmapTextureCoords(int par0, float par1, float par2) {
      if(useMultitextureARB) {
         ARBMultitexture.glMultiTexCoord2fARB(par0, par1, par2);
      } else {
         GL13.glMultiTexCoord2f(par0, par1, par2);
      }

   }
}
