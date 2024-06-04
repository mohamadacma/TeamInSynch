package com.nashss.se.teaminsynchservice.activity.results;

import com.nashss.se.teaminsynchservice.models.NewsModel;

public class GetNewsResult {
    private final NewsModel newsModel;

    private GetNewsResult(NewsModel newsModel) {
        this.newsModel = newsModel;
    }

    public NewsModel getNewsModel() {
        return newsModel;
    }

    @Override
    public String toString() {
        return "GetNewsResult{" +
                "newsModel=" + newsModel +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private NewsModel newsModel;

        public Builder withNewsModel(NewsModel newsModel) {
            this.newsModel = newsModel;
            return this;
        }

        public GetNewsResult build() {
            return new GetNewsResult(newsModel);
        }
    }
}

