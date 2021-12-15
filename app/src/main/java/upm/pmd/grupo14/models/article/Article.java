package upm.pmd.grupo14.models.article;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import upm.pmd.grupo14.common.Category;

/**
 * Article model an Article
 */
public class Article {

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

    @SerializedName("thumbnail_image")
    private String thumbnail_data;

    @SerializedName("thumbnail_media_type")
    private String thumbnail_media_type;

    @SerializedName("update_date")
    private String update_date;

    @SerializedName("username")
    private String username;

    private Image image;

    private Image thumbnail;


    /**
     * Getter of the ID
     * @return return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of the id
     * @param id the id of the article
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of the Title
     * @return return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter of the title
     * @param title the title of the article
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter of the Subtitle
     * @return return the Subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Setter of the subtitle
     * @param subtitle the subtitle of the article
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    
    /**
     * Getter of the category
     * @return return the category
     */
    public Category getCategory() {
        return category != null ? category : Category.None;
    }

    /**
     * Setter of the category
     * @param category the category of the article
     */
    public void setCategory(@NonNull String category) {
        switch(category){
            case "National":
            case "Nacional":
                this.category = Category.National;break;
            case "Economy":
            case "Economía":
                this.category = Category.Economy;break;
            case "Sports":
            case "Deportes":
                this.category = Category.Sports;break;
            case "Technology":
            case "Tecnología":
                this.category = Category.Technology;break;
            default: this.category = Category.None;break;
        }
    }

    /**
     * Getter of the resume
     * @return return the resume
     */
    public String getResume() {
        return resume;
    }

    /**
     * Setter of the resume
     * @param resume the resume of the article
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * Getter of the body
     * @return return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter of the body
     * @param body the body of the article
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Getter of the image string encoded in base64
     * @return return the image string encoded in base64
     */
    public String getImage_data() {
        return image_data;
    }

    /**
     * Setter of the image string encoded in base64
     * @param image_data the image string encoded in base64
     */
    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }

    /**
     * Getter of the extension of the image
     * @return return the extension of the image
     */
    public String getImage_media_type() {
        return image_media_type;
    }

    /**
     * Setter of the extension of the image
     * @param image_media_type the extension of the image
     */
    public void setImage_media_type(String image_media_type) {
        this.image_media_type = image_media_type;
    }

    /**
     * Getter of the string encoded in base64
     * @return return the string encoded in base64
     */
    public String getThumbnail_data() {
        return thumbnail_data;
    }

    /**
     * Setter of the string encoded in base64
     * @param thumbnail_data the thumbnail string encoded in base64 of the article
     */
    public void setThumbnail_data(String thumbnail_data) {
        this.thumbnail_data = thumbnail_data;
    }

    /**
     * Getter of the thumbnail string encoded in base64
     * @return return the thumbnail string encoded in base64
     */
    public String getThumbnail_media_type() {
        return thumbnail_media_type;
    }

    /**
     * Setter of the extension of the thumbnail 
     * @param thumbnail_media_type the extension of the thumbnail
     */
    public void setThumbnail_media_type(String thumbnail_media_type) {
        this.thumbnail_media_type = thumbnail_media_type;
    }

    /**
     * Getter of the update date
     * @return return the update date
     */
    public String getUpdate_date() {
        return update_date;
    }

    /**
     * Setter of the update date
     * @param update_date the update date of the article
     */
    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    /**
     * Getter of the username of the user of the Artcile
     * @return return the username of the user of the Article
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the username of the user of the Article
     * @param username the username of the user of the article
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the image
     * @return return the image
     */
    public Image getImage() { return image != null ? image : new Image(null,null); }

    /**
     * Setter of the image
     * @param image the image of the article
     */
    public void setImage(Image image) { this.image = image; }

    /**
     * Getter of the thumbnail
     * @return return the thumbnail
     */
    public Image getThumbnail() { return thumbnail != null ? thumbnail : new Image(null,null); }

    /**
     * Setter of the thumbnail
     * @param thumbnail the thumbnail of the article
     */
    public void setThumbnail(Image thumbnail) { this.thumbnail = thumbnail; }

    /**
     * Check if two articles are the same
     * @param article2 the Article to check
     * @return return true if this is the same article as article2 and false otherwise
     */
    public boolean equals(Article article2){
        return this.id != null && id.equals(article2.id);
    }
}
