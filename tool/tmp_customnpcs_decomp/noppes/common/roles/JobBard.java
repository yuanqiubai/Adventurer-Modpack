/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atv
 *  by
 *  uf
 *  ye
 */
package noppes.common.roles;

import java.util.List;
import noppes.client.controllers.MusicController;
import noppes.common.constants.EnumBardInstrument;
import noppes.common.entity.EntityNPCInterface;
import noppes.common.objcenter.CustomItems;
import noppes.common.roles.JobInterface;

public class JobBard
extends JobInterface {
    public int minRange = 2;
    public int maxRange = 64;
    public boolean isStreamer = true;
    public String song = "";
    public String song2 = "";
    public String song3 = "";
    public String song4 = "";
    private EnumBardInstrument instrument = EnumBardInstrument.Banjo;
    private long ticks = 0L;

    public JobBard(EntityNPCInterface npc) {
        super(npc);
        if (CustomItems.banjo != null) {
            this.mainhand = new ye(CustomItems.banjo);
            this.overrideOffHand = true;
            this.overrideMainHand = true;
        }
    }

    @Override
    public void writeEntityToNBT(by nbttagcompound) {
        nbttagcompound.a("BardSong", this.song);
        nbttagcompound.a("BardMinRange", this.minRange);
        nbttagcompound.a("BardMaxRange", this.maxRange);
        nbttagcompound.a("BardInstrument", this.instrument.ordinal());
        nbttagcompound.a("BardStreamer", this.isStreamer);
    }

    @Override
    public void readEntityFromNBT(by nbttagcompound) {
        this.song = nbttagcompound.i("BardSong");
        this.minRange = nbttagcompound.e("BardMinRange");
        this.maxRange = nbttagcompound.e("BardMaxRange");
        this.setInstrument(nbttagcompound.e("BardInstrument"));
        this.isStreamer = nbttagcompound.n("BardStreamer");
    }

    public EnumBardInstrument getInstrument() {
        return this.instrument;
    }

    public void setInstrument(int i) {
        if (CustomItems.banjo != null) {
            this.instrument = EnumBardInstrument.values()[i];
            this.overrideOffHand = this.instrument != EnumBardInstrument.None;
            this.overrideMainHand = this.overrideOffHand;
            switch (this.instrument) {
                case None: {
                    this.mainhand = null;
                    this.offhand = null;
                    break;
                }
                case Banjo: {
                    this.mainhand = new ye(CustomItems.banjo);
                    this.offhand = null;
                    break;
                }
                case Violin: {
                    this.mainhand = new ye(CustomItems.violin);
                    this.offhand = new ye(CustomItems.violinbow);
                    break;
                }
                case Guitar: {
                    this.mainhand = new ye(CustomItems.guitar);
                    this.offhand = null;
                    break;
                }
                case Harp: {
                    this.mainhand = new ye(CustomItems.harp);
                    this.offhand = null;
                }
            }
        }
    }

    public void onLivingUpdate() {
        if (this.npc.q.I) {
            ++this.ticks;
            if (this.ticks % 10L == 0L && !this.song.isEmpty()) {
                boolean isStreaming = MusicController.Instance.isStreaming();
                if (isStreaming && this.song.equals(MusicController.Instance.playing)) {
                    List list;
                    if (this.isStreamer && !(list = this.npc.q.a(uf.class, this.npc.E.b((double)this.maxRange, (double)(this.maxRange / 2), (double)this.maxRange))).contains(atv.w().h)) {
                        MusicController.Instance.stopMusic();
                    }
                } else {
                    List list = this.npc.q.a(uf.class, this.npc.E.b((double)this.minRange, (double)(this.minRange / 2), (double)this.minRange));
                    if (!list.contains(atv.w().h)) {
                        return;
                    }
                    if (this.isStreamer) {
                        MusicController.Instance.playStreaming(this.song, (float)this.npc.u, (float)this.npc.v, (float)this.npc.w);
                    } else {
                        MusicController.Instance.playMusic(this.song);
                    }
                }
            }
        }
    }

    @Override
    public void killed() {
        this.delete();
    }

    @Override
    public void delete() {
        if (this.npc.q.I && MusicController.Instance.isPlaying(this.song)) {
            MusicController.Instance.stopMusic();
        }
    }
}

