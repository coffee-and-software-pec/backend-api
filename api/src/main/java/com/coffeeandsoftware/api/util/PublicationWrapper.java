package com.coffeeandsoftware.api.util;

import com.coffeeandsoftware.api.model.Publication;

import lombok.Data;

@Data
public class PublicationWrapper implements Comparable<PublicationWrapper> {
    private Publication publication;
    private int score;

    public PublicationWrapper(Publication publication, int score) {
        this.publication = publication;
        this.score = score;
    }

    @Override
    public int compareTo(PublicationWrapper anotherWrapper) {
        return this.score - anotherWrapper.score;
    }


}
