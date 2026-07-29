package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.data.MetadataSection;

public class LanguageMetadataSection implements MetadataSection {

   private final Collection languages;

   public LanguageMetadataSection(Collection par1Collection) {
      this.languages = par1Collection;
   }

   public Collection getLanguages() {
      return this.languages;
   }
}
