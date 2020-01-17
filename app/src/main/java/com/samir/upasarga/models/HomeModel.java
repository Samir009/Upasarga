package com.samir.upasarga.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("categories_list")
    @Expose
    private List<CategoriesList> categoriesList = null;
    @SerializedName("home_list")
    @Expose
    private List<HomeList> homeList = null;
    @SerializedName("unseen_notice")
    @Expose
    private Integer unseenNotice;
    @SerializedName("total_book")
    @Expose
    private Integer totalBook;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CategoriesList> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<CategoriesList> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<HomeList> getHomeList() {
        return homeList;
    }

    public void setHomeList(List<HomeList> homeList) {
        this.homeList = homeList;
    }

    public Integer getUnseenNotice() {
        return unseenNotice;
    }

    public void setUnseenNotice(Integer unseenNotice) {
        this.unseenNotice = unseenNotice;
    }

    public Integer getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(Integer totalBook) {
        this.totalBook = totalBook;
    }


    public class CategoriesList {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public class HomeList {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("booklists")
        @Expose
        private List<Booklist> booklists = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Booklist> getBooklists() {
            return booklists;
        }

        public void setBooklists(List<Booklist> booklists) {
            this.booklists = booklists;
        }


        public class Booklist {

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
            @SerializedName("favourite")
            @Expose
            private Integer favourite;
            @SerializedName("rating")
            @Expose
            private Integer rating;
            @SerializedName("url")
            @Expose
            private String url;

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

            public Integer getFavourite() {
                return favourite;
            }

            public void setFavourite(Integer favourite) {
                this.favourite = favourite;
            }

            public Integer getRating() {
                return rating;
            }

            public void setRating(Integer rating) {
                this.rating = rating;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

        }
    }
}