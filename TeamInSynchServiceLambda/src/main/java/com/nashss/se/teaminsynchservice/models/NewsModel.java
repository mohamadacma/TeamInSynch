package com.nashss.se.teaminsynchservice.models;

import java.util.List;
import java.util.Objects;

public class NewsModel {
    private final List<String> headlines;
    private final List<String> sources;
    private final List<String> URLs;
    private final List<String> images;
    private final List<String> publishDates;

    private NewsModel( List<String> headlines, List<String> sources, List<String> URLs, List<String> images, List<String> publishDates) {
        this.headlines = headlines;
        this.sources = sources;
        this.URLs = URLs;
        this.images = images;
        this.publishDates = publishDates;
    }

    public List<String> getHeadlines() {
        return headlines;
    }

    public List<String> getSources() {
        return sources;
    }

    public List<String> getURLs() {
        return URLs;
    }
    public List<String> getImages() { return images;}

    public List<String> getPublishDates() { return publishDates; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel newsModel = (NewsModel) o;
        return  Objects.equals(headlines, newsModel.headlines) &&
                Objects.equals(sources, newsModel.sources) &&
                Objects.equals(URLs, newsModel.URLs) &&
                Objects.equals(images, newsModel.images) &&
                Objects.equals(publishDates, newsModel.publishDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headlines, sources, URLs,images,publishDates);
    }

    public static class Builder {
        private List<String> headlines;
        private List<String> sources;
        private List<String> URLs;
        private List<String> images;
        private List<String> publishDates;


        public Builder withHeadlines(List<String> headlines) {
            this.headlines = headlines;
            return this;
        }

        public Builder withSources(List<String> sources) {
            this.sources = sources;
            return this;
        }

        public Builder withURLs(List<String> URLs) {
            this.URLs = URLs;
            return this;
        }
        public Builder withImages(List<String> images) {
            this.images = images;
            return this;
        }

        public Builder withPublishDates(List<String> publishDates) {
            this.publishDates = publishDates;
            return this;
        }

        public NewsModel build() {
            return new NewsModel( headlines, sources, URLs,images,publishDates);
        }
    }
}
