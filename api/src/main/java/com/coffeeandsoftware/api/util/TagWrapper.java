package com.coffeeandsoftware.api.util;

import lombok.Data;
import com.coffeeandsoftware.api.model.Tag;

@Data
public class TagWrapper implements Comparable<TagWrapper> {
    private Tag tag;
    private int score;

    public TagWrapper(Tag tag, int score) {
        this.tag = tag;
        this.score = score;
    }

    @Override
    public int compareTo(TagWrapper anotherWrapper) {
        return this.score - anotherWrapper.score;
    }
}
