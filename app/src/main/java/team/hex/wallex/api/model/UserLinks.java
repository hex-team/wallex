package team.hex.wallex.api.model;

public class UserLinks {
    private String followers;
    private String portfolio;
    private String following;
    private String self;
    private String html;
    private String photos;
    private String likes;

    public String getFollowers() {
        return this.followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getPortfolio() {
        return this.portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getFollowing() {
        return this.following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getSelf() {
        return this.self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPhotos() {
        return this.photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return this.likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
