package upm.pmd.grupo14.models.article;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import upm.pmd.grupo14.common.Category;

public class Article {

    public Article(){
        //this.category=Category.None;
    } //TODO
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("subtitle")
    private String subtitle;

    @SerializedName("category")
    private Category category;

    @SerializedName("abstract")
    private String resume;

    @SerializedName("body")
    private String body;

    @SerializedName("image_data")
    private String image_data;

    @SerializedName("image_media_type")
    private String image_media_type;

    @SerializedName("thumbnail_data")
    private String thumbnail_data;

    @SerializedName("thumbnail_media_type")
    private String thumbnail_media_type;

    @SerializedName("update_date")
    private String update_date;

    @SerializedName("username")
    private String username;

    private Image image;

    private Image thubnail;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Category getCategory() {
        return category != null ? category : Category.None;
    }

    public void setCategory(@NonNull String category) {
        switch(category){
            case "National": this.category = Category.National;break;
            case "Economy": this.category = Category.Economy;break;
            case "Sports": this.category = Category.Sports;break;
            case "Technology": this.category = Category.Technology;break;
            default: this.category = Category.None;break;
        }
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_data() {
        return image_data;
    }

    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }

    public String getImage_media_type() {
        return image_media_type;
    }

    public void setImage_media_type(String image_media_type) {
        this.image_media_type = image_media_type;
    }

    public String getThumbnail_data() {
        return thumbnail_data;
    }

    public void setThumbnail_data(String thumbnail_data) {
        this.thumbnail_data = thumbnail_data;
    }

    public String getThumbnail_media_type() {
        return thumbnail_media_type;
    }

    public void setThumbnail_media_type(String thumbnail_media_type) {
        this.thumbnail_media_type = thumbnail_media_type;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getImage() { return image != null ? image : new Image(null,null); }

    public void setImage(Image image) { this.image = image; }

    public Image getThubnail() { return thubnail != null ? thubnail : new Image(null,null); }

    public void setThubnail(Image thubnail) { this.thubnail = thubnail; }
}
