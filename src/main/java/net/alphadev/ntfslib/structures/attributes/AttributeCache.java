package net.alphadev.ntfslib.structures.attributes;

import java.util.HashMap;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.attributes.AttributeType;

public final class AttributeCache extends HashMap<AttributeType, Attribute> {
    public final void add(Attribute attribute) {
        super.put(attribute.getType(), attribute);
    }
}