package net.minecraft.entity;

import net.minecraft.entity.Entity;

public interface EntityOwnable {

   String getOwnerName();

   Entity getOwner();
}
