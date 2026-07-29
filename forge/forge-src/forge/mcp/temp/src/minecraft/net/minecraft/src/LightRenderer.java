package net.minecraft.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;

public class LightRenderer {

   private static Minecraft minecraft;
   public static final String modVersion = "1.6.4_v1";
   public static final String[] modTitle = new String[]{"Light Renderer", "LightRenderer", "1.6.4_v1", null};
   private static final String optionsFile = "lightrenderer.txt";
   private static final String configfolder = "config";
   private File optionsFile1;
   private File optionsFile2;
   public static int[] publicTemp = new int[10];
   private boolean configDelete;
   private boolean configEnable;
   private boolean memoryReserveEnable;
   private boolean worldBlockLight;
   private final String[] optionList = new String[]{"ConfigVersion", "LightRendererConfig", "TitleEnable", "ChunkDepictionEnable", "ChunkDepictionWidth", "ChunkDepictionLength", "CircleChunkDepiction", "EmptyChunkDisregard", "RenderDistanceFog", "EntityDisplayImprovement", "EntityOcclusionCulling", "TileEntitieDepiction", "WorldBlockLight", "WorldBlockLightList", "ChunkFrustumMillTime", "ChunkAndTickSmoothFrames", "FpsMarginChunkReadBoost", "VisiblePlayer", "FpsView", "FancyStars"};
   private final String[] optionDefaultList = new String[]{"1", "true", "true", "true", "21,17,9,4", "16,10,6,4", "false", "true", "1", "true", "false", "64,48,44,40", "false", "block:1.0,sky:1.0", "15", "3", "false", "true", "false", "0"};
   private static int[] getBlockore1;


   public LightRenderer() {}

   public LightRenderer(Minecraft mc) {
      this.setLightRenderer(mc);
   }

   public void setLightRenderer(Minecraft mc) {
      if(minecraft == null) {
         minecraft = mc;
      }

      if(minecraft != null) {
         if(this.optionsFile1 == null) {
            System.out.println("Modding Loaded: " + getModTitle());
            publicTemp[0] = 1;
         }

         boolean int1 = false;
         boolean int2 = false;
         Minecraft var10000 = minecraft;
         int var5;
         if(Minecraft.field_71444_a == null) {
            var10000 = minecraft;
            Minecraft.field_71444_a = new byte[65536];
         } else {
            var10000 = minecraft;
            if(Minecraft.field_71444_a.length < 65536) {
               byte[] byte1 = new byte[65536];
               var5 = -1;
               var10000 = minecraft;
               int var6 = Minecraft.field_71444_a.length;

               while(true) {
                  ++var5;
                  if(var5 >= var6) {
                     var10000 = minecraft;
                     Minecraft.field_71444_a = byte1;
                     break;
                  }

                  Minecraft var10002 = minecraft;
                  byte1[var5] = Minecraft.field_71444_a[var5];
               }
            }
         }

         if(this.memoryReserveEnough()) {
            this.configDelete = false;
         }

         var5 = -1;

         while(true) {
            ++var5;
            if(var5 >= this.optionList.length) {
               return;
            }

            this.optionWriting(this.optionList[var5], this.optionDefaultList[var5]);
         }
      }
   }

   public static String getModTitle() {
      return modTitle[1] + '_' + modTitle[2];
   }

   public void firstExecution() {
      if(this.memoryReserveEnough()) {
         this.loadOptions();
      }

   }

   public boolean memoryReserveEnough() {
      Minecraft var10000 = minecraft;
      byte[] byte1 = Minecraft.field_71444_a;
      this.memoryReserveEnable = byte1 != null && byte1.length >= 65536;
      if(this.memoryReserveEnable) {
         ++byte1['\uffdc'];
      }

      return this.memoryReserveEnable;
   }

   public void optionWriting(String string1, String string2) {
      if(minecraft != null) {
         int int2;
         try {
            if(this.optionList.length != this.optionDefaultList.length) {
               this.systemConsoleMessage("Disagreement of \"optionList\" and \"optionDefaultList\"", true);
            }

            byte throwable = -1;
            int var6 = throwable + 1;
            if(string1.equals(this.optionList[var6])) {
               this.configDelete = Integer.parseInt(this.optionDefaultList[var6]) != Integer.parseInt(string2);
            } else {
               ++var6;
               if(string1.equals(this.optionList[var6])) {
                  this.configEnable = string2.equals("true");
               } else if(this.memoryReserveEnable) {
                  ++var6;
                  Minecraft var10000;
                  if(string1.equals(this.optionList[var6])) {
                     var10000 = minecraft;
                     Minecraft.field_71444_a['\uffe8'] = (byte)(string2.equals("true")?2:1);
                  } else {
                     ++var6;
                     if(string1.equals(this.optionList[var6])) {
                        var10000 = minecraft;
                        Minecraft.field_71444_a['\uffde'] = (byte)(string2.equals("true")?1:0);
                     } else {
                        ++var6;
                        Minecraft var10001;
                        if(string1.equals(this.optionList[var6])) {
                           var10001 = minecraft;
                           this.chunkDepiction(Minecraft.field_71444_a['\uffde'] == 1?string2:"26,17,9,4", '\uff78');
                        } else {
                           ++var6;
                           if(string1.equals(this.optionList[var6])) {
                              var10001 = minecraft;
                              this.chunkDepiction(Minecraft.field_71444_a['\uffde'] == 1?string2:"16,16,16,16", '\uff7d');
                           } else {
                              ++var6;
                              if(string1.equals(this.optionList[var6])) {
                                 var10000 = minecraft;
                                 Minecraft.field_71444_a['\uffe3'] = (byte)(string2.equals("true")?2:1);
                              } else {
                                 ++var6;
                                 if(string1.equals(this.optionList[var6])) {
                                    var10000 = minecraft;
                                    Minecraft.field_71444_a['\uffe2'] = (byte)(string2.equals("true")?2:1);
                                 } else {
                                    ++var6;
                                    if(string1.equals(this.optionList[var6])) {
                                       int2 = (int2 = Integer.parseInt(string2)) > 127?127:(int2 <= 0?0:int2);
                                       var10000 = minecraft;
                                       Minecraft.field_71444_a['\uffdd'] = (byte)(int2 + 1 & 127);
                                    } else {
                                       ++var6;
                                       if(string1.equals(this.optionList[var6])) {
                                          var10000 = minecraft;
                                          Minecraft.field_71444_a['\uffdf'] = (byte)(string2.equals("true")?2:1);
                                       } else {
                                          ++var6;
                                          if(string1.equals(this.optionList[var6])) {
                                             var10000 = minecraft;
                                             Minecraft.field_71444_a['\uffe0'] = (byte)(string2.equals("true")?2:(!string2.equals("fancy") && !string2.equals("Fancy") && !string2.equals("FANCY")?1:3));
                                          } else {
                                             ++var6;
                                             if(string1.equals(this.optionList[var6])) {
                                                this.chunkDepiction(string2, '\ufffb');
                                             } else {
                                                ++var6;
                                                if(string1.equals(this.optionList[var6])) {
                                                   this.worldBlockLight = string2.equals("true");
                                                } else {
                                                   ++var6;
                                                   if(string1.equals(this.optionList[var6])) {
                                                      this.worldBlockLightFunction(string2, '\uff8c');
                                                   } else {
                                                      ++var6;
                                                      if(string1.equals(this.optionList[var6])) {
                                                         int2 = (int2 = Integer.parseInt(string2)) > 127?127:(int2 <= 0?0:int2);
                                                         var10000 = minecraft;
                                                         Minecraft.field_71444_a['\uffe1'] = (byte)int2;
                                                      } else {
                                                         ++var6;
                                                         if(string1.equals(this.optionList[var6])) {
                                                            int2 = (int2 = Integer.parseInt(string2)) > 127?127:(int2 <= 0?0:int2);
                                                            var10000 = minecraft;
                                                            Minecraft.field_71444_a['\uffe4'] = (byte)int2;
                                                         } else {
                                                            ++var6;
                                                            if(string1.equals(this.optionList[var6])) {
                                                               var10000 = minecraft;
                                                               Minecraft.field_71444_a['\uffeb'] = (byte)(string2.equals("true")?2:1);
                                                            } else {
                                                               ++var6;
                                                               if(string1.equals(this.optionList[var6])) {
                                                                  var10000 = minecraft;
                                                                  Minecraft.field_71444_a['\uffe5'] = (byte)(string2.equals("true")?2:1);
                                                               } else {
                                                                  ++var6;
                                                                  if(string1.equals(this.optionList[var6])) {
                                                                     var10000 = minecraft;
                                                                     Minecraft.field_71444_a['\uffe6'] = (byte)(string2.equals("true")?2:1);
                                                                  } else {
                                                                     ++var6;
                                                                     if(string1.equals(this.optionList[var6])) {
                                                                        int2 = (int2 = Integer.parseInt(string2)) > 255?255:(int2 <= 0?0:int2);
                                                                        var10000 = minecraft;
                                                                        Minecraft.field_71444_a['\uffe7'] = (byte)int2;
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (Throwable var5) {
            var5.printStackTrace();
            this.systemConsoleMessage("Skipping bad string option: " + string1 + " , " + string2, true);
            if(publicTemp[1] < 3) {
               ++publicTemp[1];

               for(int2 = 0; int2 < this.optionList.length; ++int2) {
                  if(string1.equals(this.optionList[int2])) {
                     this.optionWriting(this.optionList[int2], this.optionDefaultList[int2]);
                     break;
                  }
               }
            } else {
               this.systemConsoleMessage("The stop by the loop of an option error: " + string1 + " , " + string2, true);
            }
         }

         publicTemp[1] = 0;
      }
   }

   private void loadOptions() {
      if(minecraft != null) {
         try {
            if(this.optionsFile1 == null) {
               this.optionsFile1 = new File(minecraft.field_71412_D, "config");
            }

            if(!this.optionsFile1.exists()) {
               this.systemConsoleMessage("Failed to load options folder: [" + "config" + "]", true);
               return;
            }

            if(this.optionsFile2 == null) {
               this.optionsFile2 = new File(this.optionsFile1, "lightrenderer.txt");
            }

            if(this.configDelete || !this.optionsFile2.exists()) {
               PrintWriter throwable = new PrintWriter(new FileWriter(this.optionsFile2));
               throwable.println("# " + modTitle[0] + " Config");
               throwable.close();
               this.configDelete = false;
               this.optionsFile2 = new File(this.optionsFile1, "lightrenderer.txt");
            }

            if(this.optionsFile2.exists()) {
               BufferedReader var14 = new BufferedReader(new FileReader(this.optionsFile2));
               int int1 = 0;
               int int2 = this.optionList.length;
               String string2 = "";
               String string3 = System.getProperty("line.separator");
               boolean[] boolean2 = new boolean[this.optionList.length];
               String string1 = "";

               String[] string6;
               while((string1 = var14.readLine()) != null && int1 < this.optionList.length) {
                  boolean throwable1 = false;

                  try {
                     if(!throwable1 && this.isEmpty(string1)) {
                        if(string1.indexOf("=") != -1) {
                           string6 = string1.split("=");
                           if(string6.length > 1 && this.isEmpty(string6[0]) && this.isEmpty(string6[1]) && !string6[0].equals(this.optionList[(int1 + 1) % this.optionList.length])) {
                              for(int int3 = 0; int3 < this.optionList.length; ++int3) {
                                 if(string6[0].equals(this.optionList[int3])) {
                                    if(!boolean2[int3]) {
                                       string2 = string2 + string6[0] + "=" + string6[1];
                                       throwable1 = true;
                                       boolean2[int3] = true;
                                       ++int1;
                                    }
                                    break;
                                 }
                              }
                           }
                        } else if(string1.indexOf("#") != -1) {
                           string2 = string2 + string1;
                           throwable1 = true;
                        }

                        if(throwable1) {
                           string2 = string2 + string3;
                        }
                     }
                  } catch (Throwable var12) {
                     throwable1 = false;
                     boolean2[int1] = false;
                  }

                  if(!throwable1 && !boolean2[int1]) {
                     if(int1 < int2) {
                        string2 = string2 + this.optionList[int1] + "=" + this.optionDefaultList[int1] + string3;
                     }

                     throwable1 = true;
                     boolean2[int1] = true;
                     ++int1;
                  }
               }

               var14.close();

               for(int var15 = 0; var15 < int2 && var15 < boolean2.length; ++var15) {
                  if(!boolean2[var15]) {
                     boolean2[var15] = true;
                     string2 = string2 + this.optionList[var15] + "=" + this.optionDefaultList[var15] + string3;
                  }
               }

               if(this.isEmpty(string2)) {
                  PrintWriter var16 = new PrintWriter(new BufferedWriter(new FileWriter(this.optionsFile2)));
                  var16.print(string2);
                  var16.close();
               }

               var14 = new BufferedReader(new FileReader(this.optionsFile2));
               string1 = "";

               while((string1 = var14.readLine()) != null) {
                  try {
                     if(this.isEmpty(string1) && string1.indexOf("=") != -1 && string1.indexOf("#") == -1) {
                        String[] var17 = string1.split("=");
                        if(var17.length > 1 && this.isEmpty(var17[0]) && this.isEmpty(var17[1])) {
                           if(var17[1].indexOf("#") != -1) {
                              string6 = var17[1].split("#");
                              var17[1] = string6[0];
                           }

                           if(this.configEnable) {
                              this.optionWriting(var17[0], var17[1]);
                           }
                        }
                     }
                  } catch (Throwable var11) {
                     this.systemConsoleMessage("Skipping bad option text: [" + string1 + "]", true);
                  }
               }

               var14.close();
            } else {
               this.systemConsoleMessage("Failed to load options file: [" + this.optionsFile2 + "]", true);
            }

            publicTemp[2] = 1;
         } catch (Throwable var13) {
            this.systemConsoleMessage("Reading of a file went wrong: [" + this.optionsFile2 + "]", true);
            var13.printStackTrace();
            this.configDelete = false;
         }

         if(this.configDelete) {
            this.loadOptions();
         }

      }
   }

   private void chunkDepiction(String string1, int int1) {
      if(this.isEmpty(string1)) {
         if(string1.indexOf(",") == -1) {
            string1 = string1 + ",";
         }

         if(string1.indexOf(",") != -1 && this.memoryReserveEnable) {
            String[] string2 = string1.split(",");

            for(int int2 = 0; int2 < string2.length; ++int2) {
               if(this.isEmpty(string2[int2])) {
                  byte byte1 = (byte1 = (byte)Integer.parseInt(string2[int2])) > 127?127:(byte1 <= 0?0:byte1);
                  Minecraft var10000 = minecraft;
                  Minecraft.field_71444_a[int1 + int2] = byte1;
               }
            }
         }

      }
   }

   private void worldBlockLightFunction(String string1, int int1) {
      if(this.worldBlockLight && this.isEmpty(string1)) {
         if(string1.indexOf(",") == -1) {
            string1 = string1 + ",";
         }

         if(string1.indexOf(",") != -1 && this.memoryReserveEnable) {
            String[] string5 = string1.split(",");
            boolean int4 = false;

            for(int int3 = 0; int3 < string5.length; ++int3) {
               if(this.isEmpty(string5[int3]) && string5[int3].indexOf(":") != -1) {
                  String[] string7 = string5[int3].split(":");
                  if(string7.length > 1 && this.isEmpty(string7[0]) && this.isEmpty(string7[1])) {
                     float float1 = Float.parseFloat(string7[1]);
                     float1 = float1 < 0.0F?0.0F:(float1 > 1.0F?1.0F:float1);
                     Minecraft var10000;
                     if(!string7[0].equals("block") && !string7[0].equals("Block") && !string7[0].equals("BLOCK")) {
                        if(string7[0].equals("BCK_min")) {
                           var10000 = minecraft;
                           Minecraft.field_71444_a[int1 + 1] = (byte)((int)(float1 * 127.0F));
                        } else if(string7[0].equals("BCK_max")) {
                           var10000 = minecraft;
                           Minecraft.field_71444_a[int1 + 2] = (byte)((int)(float1 * 127.0F));
                        } else if(string7[0].equals("DebugMode")) {
                           var10000 = minecraft;
                           Minecraft.field_71444_a['\uffe9'] = (byte)Integer.parseInt(string7[1]);
                           if(string7.length >= 3 && !string7[2].isEmpty()) {
                              var10000 = minecraft;
                              Minecraft.field_71444_a['\uffea'] = (byte)Integer.parseInt(string7[2]);
                           }

                           PrintStream var8 = System.out;
                           StringBuilder var10001 = (new StringBuilder()).append(string7[0]).append("_");
                           Minecraft var10002 = minecraft;
                           var10001 = var10001.append(Minecraft.field_71444_a['\uffe9']).append("_");
                           var10002 = minecraft;
                           var8.println(var10001.append(Minecraft.field_71444_a['\uffea']).toString());
                        } else {
                           float1 = Float.parseFloat(string7[1]);
                           float1 = float1 < 0.0F?0.0F:(float1 > 10.0F?10.0F:float1);
                           if(!string7[0].equals("sky") && !string7[0].equals("Sky") && !string7[0].equals("SKY")) {
                              if(!string7[0].equals("skymin") && !string7[0].equals("SkyMin") && !string7[0].equals("SKYMIN")) {
                                 if(string7[0].equals("skymax") || string7[0].equals("SkyMax") || string7[0].equals("SKYMAX")) {
                                    var10000 = minecraft;
                                    Minecraft.field_71444_a[int1 + 5] = (byte)((int)(float1 / 10.0F * 127.0F));
                                 }
                              } else {
                                 var10000 = minecraft;
                                 Minecraft.field_71444_a[int1 + 4] = (byte)((int)(float1 / 10.0F * 127.0F));
                              }
                           } else {
                              var10000 = minecraft;
                              Minecraft.field_71444_a[int1 + 3] = (byte)((int)(float1 / 10.0F * 127.0F));
                           }
                        }
                     } else {
                        var10000 = minecraft;
                        Minecraft.field_71444_a[int1 + 0] = (byte)((int)(float1 * 127.0F));
                     }
                  }
               }
            }
         }

      } else {
         this.worldBlockLight = false;
      }
   }

   public int[] getChunkDepiction(int par1, int par2, int par3) {
      if(minecraft == null) {
         return new int[]{4 << 3 - par3 > 21?21:4 << 3 - par3, (3 - par3 + 1) * (3 - par3) + 4, par3};
      } else {
         int[] int3 = new int[]{21, 17, 9, 4, 4, 16, 10, 6, 4, 4};
         int int2 = par3 < 0?0:(par3 > 4?4:par3);
         if(this.memoryReserveEnable) {
            for(int int4 = 0; int4 < 10; ++int4) {
               Minecraft var10000 = minecraft;
               if(Minecraft.field_71444_a[int4 + '\uff78'] > 0) {
                  Minecraft var10002 = minecraft;
                  int3[int4] = Minecraft.field_71444_a[int4 + '\uff78'];
               }
            }
         }

         int int1 = int3[int2] < 1?1:(int3[int2] > 81?81:int3[int2]);
         int2 = int3[int2 + 5] < 1?1:(int3[int2 + 5] > 16?16:int3[int2 + 5]);
         if(int1 * int1 * int2 > par1 * par1 * par2) {
            int1 = par1;
            int2 = par2;
         }

         if(minecraft.field_71474_y.field_74349_h && !minecraft.field_71474_y.field_74337_g && int1 * int1 * int2 < 20) {
            int1 = 3;
            int2 = 3;
         }

         return new int[]{int1, int2, par3};
      }
   }

   public byte[] lightRendererConfig1(byte[] byte1, int int1, int int2) {
      if(byte1 != null && int2 - int1 >= 0 && int2 - int1 < byte1.length && this.memoryReserveEnable) {
         for(int int5 = int1; int5 <= int2; ++int5) {
            if(int5 - int1 < byte1.length && int5 < 65536) {
               int var10001 = int5 - int1;
               Minecraft var10002 = minecraft;
               byte1[var10001] = Minecraft.field_71444_a[int5];
            }
         }
      }

      return byte1;
   }

   public boolean systemConsoleMessage(String string1, boolean boolean1) {
      if(minecraft != null && string1 != null && !string1.isEmpty() && string1 != "") {
         boolean1 = false;
         if(!boolean1) {
            System.out.println("[" + modTitle[0] + "] " + string1);
         } else {
            System.out.println(string1);
         }

         return true;
      } else {
         return false;
      }
   }

   public String playerLanguageAcquisition(EntityPlayer entityplayer) {
      String string1 = minecraft != null?minecraft.field_71474_y.field_74363_ab:null;
      if(string1 == null || string1.isEmpty() || string1 == "") {
         string1 = "en_US";
      }

      return string1;
   }

   public boolean minecraftGameMessage(EntityPlayer entityplayer, String[] string1, String[][] string2) {
      if(minecraft != null && minecraft.field_71456_v != null && minecraft.field_71456_v.func_73827_b() != null && string1 != null && string2 != null && (entityplayer != null || minecraft.field_71451_h == null || !(minecraft.field_71451_h instanceof Entity))) {
         String string3 = this.playerLanguageAcquisition((EntityPlayer)minecraft.field_71451_h);
         if(string3 != null && string3 != "") {
            int int1 = string1.length - 1;
            int int2 = -1;
            if(0 <= string1.length - 1) {
               while(true) {
                  ++int2;
                  if(int2 == string1.length - 1) {
                     break;
                  }

                  if(string3.equals(string1[int2])) {
                     int1 = int2;
                     break;
                  }
               }
            }

            int2 = -1;

            while(true) {
               ++int2;
               if(int2 == string2[int1].length) {
                  return true;
               }

               if(string2[int1][int2] != null) {
                  minecraft.field_71456_v.func_73827_b().func_73765_a(string2[int1][int2]);
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private String getFileText(String string1, boolean boolean1) {
      if(minecraft == null) {
         return "";
      } else {
         String string2 = boolean1?System.getProperty("line.separator"):"|";
         StringBuilder stringBuilder = new StringBuilder();
         if(!boolean1) {
            stringBuilder.append("[").append(string1).append("] Input :").append(System.getProperty("line.separator")).append("      ");
         }

         try {
            File throwable = new File(minecraft.field_71412_D, string1);
            if(throwable.exists()) {
               BufferedReader bufferedreader = new BufferedReader(new FileReader(throwable));
               String string3 = "";

               while((string3 = bufferedreader.readLine()) != null && stringBuilder.length() < '\uffff') {
                  stringBuilder.append(string3).append(string2);
               }

               bufferedreader.close();
            }
         } catch (Throwable var8) {
            return "";
         }

         return stringBuilder != null?stringBuilder.toString():"";
      }
   }

   public static String[] getDebugMode() {
      String[] string1 = new String[10];

      for(int throwable = 0; throwable < string1.length; ++throwable) {
         string1[throwable] = "";
      }

      Minecraft var10000 = minecraft;
      if(Minecraft.field_71444_a['\uffe9'] == 1) {
         string1[0] = "DebugMode";
         StringBuilder var10002 = (new StringBuilder()).append(" version:");
         Minecraft var10003 = minecraft;
         string1[1] = var10002.append(Minecraft.func_110431_a(minecraft)).append(" , multiMode:").append(!minecraft.func_71356_B() || minecraft.func_71401_C().func_71344_c()).toString();
      } else {
         var10000 = minecraft;
         int double2;
         if(Minecraft.field_71444_a['\uffe9'] == 2 && minecraft.field_71441_e != null) {
            string1[0] = "OreChecker";
            EntityPlayer var21 = (EntityPlayer)minecraft.field_71451_h;
            int var23;
            if(getBlockore1 == null) {
               getBlockore1 = new int[Block.field_71973_m.length];
               int getBlockore2 = 0;

               for(var23 = 1; getBlockore2 < getBlockore1.length; ++getBlockore2) {
                  if(Block.field_71973_m[getBlockore2] != null && Block.field_71973_m[getBlockore2].func_71858_a(0, 0) != null) {
                     String var24 = Block.field_71973_m[getBlockore2].func_71858_a(0, 0).func_94215_i();
                     if(var24 != null && (var24.indexOf("Ore") != -1 || var24.indexOf("ore") != -1)) {
                        getBlockore1[getBlockore2] = var23++;
                     }
                  }
               }

               publicTemp[3] = var23;
            }

            int[][] var22 = new int[publicTemp[3]][2];
            var23 = (int)var21.field_70142_S;
            int var25 = (int)var21.field_70137_T;
            int var26 = (int)var21.field_70136_U;
            double2 = 0;
            int var28 = 0;
            int var30 = 0;
            int var31 = 0;
            double var33 = 1.073741824E9D;
            var10000 = minecraft;
            byte var34 = Minecraft.field_71444_a['\uffea'];
            if(var34 == 0) {
               var34 = 16;
            }

            string1[0] = string1[0] + " " + var34;

            int int11;
            int int2;
            for(int11 = var23 - var34; int11 < var23 + var34; ++int11) {
               for(int string2 = var25 - var34; string2 < var25 + var34; ++string2) {
                  for(int2 = var26 - var34; int2 < var26 + var34; ++int2) {
                     int int4 = minecraft.field_71441_e.func_72798_a(int11, string2, int2);
                     if(getBlockore1[int4] > 0) {
                        ++var22[getBlockore1[int4]][0];
                        var22[getBlockore1[int4]][1] = int4;
                        double double1 = (double)(Math.abs((int11 - var23) * (int11 - var23)) + Math.abs((string2 - var25) * (string2 - var25)) + Math.abs((int2 - var26) * (int2 - var26)));
                        if(double1 < var33) {
                           var33 = double1;
                           double2 = int4;
                           var28 = int11 - var23;
                           var30 = string2 - var25;
                           var31 = int2 - var26;
                        }
                     }
                  }
               }
            }

            if(double2 > 0) {
               string1[1] = Block.field_71973_m[double2].func_71858_a(0, 0).func_94215_i() + " , [x:" + var28 + " y:" + var30 + " z:" + var31 + "]";
            }

            for(int11 = 1; int11 < var22.length; ++int11) {
               if(var22[int11][0] > 0) {
                  String var35 = Block.field_71973_m[var22[int11][1]].func_71858_a(0, 0).func_94215_i();
                  int2 = 2 + int11 / 4;
                  string1[int2] = string1[int2] + var35 + ":" + var22[int11][0] + " , ";
               }
            }
         } else {
            var10000 = minecraft;
            if(Minecraft.field_71444_a['\uffe9'] == 3 && minecraft.field_71441_e != null) {
               string1[0] = "EntityStatusChecker";

               try {
                  double var20 = 4096.0D;
                  EntityPlayer entityPlayer = (EntityPlayer)minecraft.field_71451_h;
                  List entitylist = minecraft.field_71441_e.func_72910_y();
                  EntityLivingBase entity = null;

                  for(double2 = 0; double2 < entitylist.size(); ++double2) {
                     Entity entity1 = (Entity)entitylist.get(double2);
                     double attributeList = entity1.func_70092_e(entityPlayer.field_70142_S, entityPlayer.field_70137_T, entityPlayer.field_70136_U);
                     if(entityPlayer != entity1 && entity1 instanceof EntityLivingBase && attributeList < var20) {
                        var20 = attributeList;
                        entity = (EntityLivingBase)entity1;
                     }
                  }

                  if(entity != null) {
                     string1[1] = entity.func_70023_ak() + " [" + entity.field_70157_k + "],[" + "x:" + (int)entity.field_70142_S + " y:" + (int)entity.field_70137_T + " z:" + (int)entity.field_70136_U + "],[" + entity.getClass().getName() + "]";
                     string1[2] = "x:" + (int)(entityPlayer.field_70142_S - entity.field_70142_S) + " y:" + (int)(entityPlayer.field_70137_T - entity.field_70137_T) + " z:" + (int)(entityPlayer.field_70136_U - entity.field_70136_U);
                     string1[3] = "HP:" + (int)entity.func_110143_aJ() + " EXP:" + entity.func_70693_a(entityPlayer);
                     double var27 = 0.0D;
                     ArrayList var29 = new ArrayList();
                     ArrayList stringList = new ArrayList();
                     var29.add(SharedMonsterAttributes.field_111267_a);
                     var29.add(SharedMonsterAttributes.field_111264_e);
                     var29.add(SharedMonsterAttributes.field_111263_d);
                     stringList.add("maxHealth");
                     if(entity instanceof EntityHorse) {
                        stringList.add("jumpStrength");
                        EntityHorse int1 = (EntityHorse)entity;
                        var27 = int1.func_110215_cj();
                     } else {
                        stringList.add("attackDamage");
                     }

                     stringList.add("movementSpeed");
                     string1[4] = "[";

                     for(int var32 = 0; var32 < var29.size(); ++var32) {
                        Attribute attribute = (Attribute)var29.get(var32);
                        AttributeInstance attributeInstance = attribute != null?entity.func_110148_a(attribute):null;
                        string1[4] = string1[4] + (String)stringList.get(var32);
                        string1[4] = string1[4] + ":";
                        string1[4] = string1[4] + (attributeInstance != null?(float)MathHelper.func_76128_c(attributeInstance.func_111125_b() * 100000.0D) / 100000.0F:(float)MathHelper.func_76128_c(var27 * 1000000.0D) / 1000000.0F);
                        if(var32 < var29.size() - 1) {
                           string1[4] = string1[4] + ",";
                        }
                     }

                     string1[4] = string1[4] + "]";
                  }
               } catch (Throwable var19) {
                  string1[0] = string1[0] + "[Error]";
               }
            }
         }
      }

      return string1;
   }

   public void lightRendererClassErrorCheck() {
      if(minecraft != null && modTitle[3] == null) {
         String string1 = System.getProperty("line.separator") + "      ";
         String string2 = "";
         String string3 = "";
         String string4 = this.playerLanguageAcquisition((EntityPlayer)minecraft.field_71451_h);
         String[] string5 = null;
         Object string6 = null;
         byte int1 = 1;

         try {
            if("ja_JP".equals(string4)) {
               string5 = new String[]{"\u306e\u30a8\u30e9\u30fc\u30c1\u30a7\u30c3\u30af\u306b\u3088\u308b\u554f\u984c\u3092\u898b\u3064\u3051\u307e\u3057\u305f", "\u8a2d\u5b9a\u306e\u8aad\u307f\u8fbc\u307f\u306b\u5931\u6557\u3057\u307e\u3057\u305f", "OptiFine\u306e\u5c0e\u5165\u304c\u5fc5\u8981\u3067\u3059", "OptiFine\u306e\u5c0e\u5165\u306f\u9069\u5207\u3067\u306f\u3042\u308a\u307e\u305b\u3093", "OptiFine HD Ultra\u3092\u4f7f\u7528\u3057\u3066\u304f\u3060\u3055\u3044", "OptiFine\u306e\u30aa\u30d7\u30b7\u30e7\u30f3\u304c\u8aad\u307f\u8fbc\u307e\u308c\u307e\u305b\u3093", "\u30a8\u30e9\u30fc\u30c1\u30a7\u30c3\u30af\u3092\u7d42\u4e86\u3057\u307e\u3057\u305f"};
            } else {
               string5 = new String[]{"has problem by error checking.", "Reading of a setup went wrong.", "Please introduce OptiFine.", "Introduction of OptiFine is not appropriate.", "Please use OptiFine HD Ultra.", "The option of OptiFine is not read.", "Error checking was ended."};
            }

            String[] var10000;
            if("ja_JP".equals(string4)) {
               var10000 = new String[]{"", "Minecraft\u306f\u58ca\u308c\u3066\u3044\u308b\u304b\u3082\u3057\u308c\u307e\u305b\u3093", "mods\u3092\u4f7f\u7528\u3057\u3066\u3044\u308b\u5834\u5408\u306f\u30d5\u30a1\u30a4\u30eb\u540d\u3092OptiFine\u306e\u5f8c\u308d\u306b\u8a2d\u5b9a\u3057\u3066\u304f\u3060\u3055\u3044", "", "", "", ""};
            } else {
               var10000 = new String[]{"", "Minecraft may have broken.", "When mods is being used, please set up a file name behind OptiFine.", "", "", "", ""};
            }

            string3 = string3 + (this.memoryReserveEnable?"1":"0");
            string3 = string3 + (this.optionsFile1 != null?"1":"0");
            string3 = string3 + (this.optionsFile2 != null?"1":"0");
            string3 = string3 + publicTemp[0];
            string3 = string3 + publicTemp[1];
            string3 = string3 + publicTemp[2];
            string3 = string3 + "-" + string4 + "-";
            string3 = string3 + ("Default".equals(minecraft.field_71474_y.field_74346_m)?"1":"0");
            string3 = string3 + (minecraft.field_71474_y.field_74349_h && !minecraft.field_71474_y.field_74337_g?"1":"0");
            string3 = string3 + (minecraft.field_71474_y.field_74347_j?"1":"0");
            string3 = string3 + (minecraft.field_71474_y.field_74345_l?"1":"0");
            string3 = string3 + (minecraft.field_71474_y.field_74353_u?"1":"0");
            string3 = string3 + minecraft.field_71474_y.field_74339_e;
            string3 = string3 + minecraft.field_71474_y.field_74348_k;
            string3 = string3 + "-";

            for(int throwable = '\ufff0'; throwable <= '\ufffa'; ++throwable) {
               StringBuilder var12 = (new StringBuilder()).append(string3);
               Object var13;
               if(this.memoryReserveEnable) {
                  Minecraft var10001 = minecraft;
                  var13 = Byte.valueOf(Minecraft.field_71444_a[throwable]);
               } else {
                  var13 = "-1";
               }

               string3 = var12.append(var13).toString();
            }

            int var11 = string5.length - 1;
         } catch (Throwable var10) {
            if(string5 == null || string6 == null) {
               return;
            }

            String string0 = string1 + "******************************" + string1 + modTitle[1] + '_' + modTitle[2] + " " + string5[0] + string1 + string1 + string5[int1] + string1 + string1 + ((Object[])string6)[int1] + string1 + string1 + string1 + "Report :" + string3 + string1 + "******************************" + string1;
            string0 = string0 + string1 + this.getFileText("config\\lightrenderer.txt", false) + string1;
            string0 = string0 + string1 + this.getFileText("options.txt", false) + string1;
            this.lightRendererErrorMessage(var10, string0);
         }

         modTitle[3] = "Author by Aurora";
      }
   }

   public void lightRendererErrorMessage(Throwable throwable, String string1) {
      throw new ReportedException(CrashReport.func_85055_a(throwable, string1));
   }

   private boolean isEmpty(String string1) {
      return string1 != null && !string1.isEmpty();
   }

}
