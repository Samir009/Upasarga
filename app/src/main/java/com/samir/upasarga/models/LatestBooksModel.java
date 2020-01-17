package com.samir.upasarga.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestBooksModel implements Serializable{

@SerializedName("data")
@Expose
private List<Datum> data = null;
@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}


    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("author_name")
        @Expose
        private String authorName;
        @SerializedName("publish_by")
        @Expose
        private String publishBy;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("featured_image")
        @Expose
        private String featuredImage;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("comment")
        @Expose
        private List<Object> comment = null;
        @SerializedName("favourite")
        @Expose
        private Integer favourite;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("favourite_count")
        @Expose
        private Integer favouriteCount;
        @SerializedName("category")
        @Expose
        private String category;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getPublishBy() {
            return publishBy;
        }

        public void setPublishBy(String publishBy) {
            this.publishBy = publishBy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFeaturedImage() {
            return featuredImage;
        }

        public void setFeaturedImage(String featuredImage) {
            this.featuredImage = featuredImage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Object> getComment() {
            return comment;
        }

        public void setComment(List<Object> comment) {
            this.comment = comment;
        }

        public Integer getFavourite() {
            return favourite;
        }

        public void setFavourite(Integer favourite) {
            this.favourite = favourite;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getFavouriteCount() {
            return favouriteCount;
        }

        public void setFavouriteCount(Integer favouriteCount) {
            this.favouriteCount = favouriteCount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

    }

}