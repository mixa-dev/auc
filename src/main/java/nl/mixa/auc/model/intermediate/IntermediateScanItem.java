package nl.mixa.auc.model.intermediate;

import lombok.Data;
import nl.mixa.auc.parsing.IntermediateBuilderHelper;

import java.util.List;

@Data
public class IntermediateScanItem {

    private String displayName;
    private int itemLvl;
    private int unknown1;
    private int unknown111;
    private String unknown2;
    private int probablyCurrentBid;
    private int unknown;
    private long seenDate;
    private String itemName;
    private String unknown3;
    private int stackSize;
    private int unknown4;
    private boolean unknown5;
    private int unknown6;
    private long bid;
    private int unknown7;
    private long buyOut;
    private int unknown8;
    private boolean unknown9;
    private String seller;
    private int unknown10;
    private String unknown11;
    private long itemId;
    private int unknown12;
    private int unknown13;
    private int unknown14;
    private int unknown15;

    /** Mapping of item in rope-array to field in this class
     * */
    public static final List<IntermediateBuilderHelper.IntermediateScanHelperBuilder<?>> ROPE_MAPPING = List.of(
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<String>().setter(Object::toString, intermediateScanItem -> intermediateScanItem::setDisplayName),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setItemLvl),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown1),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown111),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<String>().setter(Object::toString, intermediateScanItem -> intermediateScanItem::setUnknown2),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setProbablyCurrentBid),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Long>().setter(it -> Long.parseLong(it.toString()), intermediateScanItem -> intermediateScanItem::setSeenDate),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<String>().setter(Object::toString, intermediateScanItem -> intermediateScanItem::setItemName),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<String>().setter(Object::toString, intermediateScanItem -> intermediateScanItem::setUnknown3),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setStackSize),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown4),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Boolean>().setter(it -> Boolean.valueOf(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown5),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown6),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Long>().setter(it -> Long.parseLong(it.toString()), intermediateScanItem -> intermediateScanItem::setBid),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown7),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Long>().setter(it -> Long.parseLong(it.toString()), intermediateScanItem -> intermediateScanItem::setBuyOut),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown8),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Boolean>().setter(it -> Boolean.valueOf(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown9),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<String>().setter(Object::toString, intermediateScanItem -> intermediateScanItem::setSeller),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown10),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<String>().setter(Object::toString, intermediateScanItem -> intermediateScanItem::setUnknown11),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Long>().setter(it -> Long.parseLong(it.toString()), intermediateScanItem -> intermediateScanItem::setItemId),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown12),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown13),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown14),
            new IntermediateBuilderHelper.IntermediateScanHelperBuilder<Integer>().setter(it -> Integer.parseInt(it.toString()), intermediateScanItem -> intermediateScanItem::setUnknown15)
    );
}
