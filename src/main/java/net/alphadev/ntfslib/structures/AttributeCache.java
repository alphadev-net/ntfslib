package net.alphadev.ntfslib.structures;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.attributes.AttributeType;

import java.util.HashMap;

public final class AttributeCache extends HashMap<AttributeType, Attribute> {
    public final void add(Attribute attribute) {
        super.put(attribute.getType(), attribute);
    }
}