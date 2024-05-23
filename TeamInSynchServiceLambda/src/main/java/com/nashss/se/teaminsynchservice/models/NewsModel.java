package com.nashss.se.teaminsynchservice.models;

import java.util.List;
import java.util.Objects;

public class NewsModel {
    private final String city;
    private final List<String> headlines;
    private final List<String> sources;
    private final List<String> URLs;

    private NewsModel(String city, List<String> headlines, List<String> sources, List<String> URLs) {
        this.city = city;
        this.headlines = headlines;
        this.sources = sources;
        this.URLs = URLs;
    }

    // Getters for each field
    public String getCity() {
        return city;
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

    // Override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel newsModel = (NewsModel) o;
        return Objects.equals(city, newsModel.city) &&
                Objects.equals(headlines, newsModel.headlines) &&
                Objects.equals(sources, newsModel.sources) &&
                Objects.equals(URLs, newsModel.URLs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, headlines, sources, URLs);
    }

    public static class Builder {
        private String city;
        private List<String> headlines;
        private List<String> sources;
        private List<String> URLs;

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

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

        public NewsModel build() {
            return new NewsModel(city, headlines, sources, URLs);
        }
    }
}
