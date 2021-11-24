package upm.pmd.grupo14.models.article;

import com.google.gson.annotations.SerializedName;

import upm.pmd.grupo14.common.Category;

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

    @SerializedName("thumbnail_data")
    private String thumbnail_data;

    @SerializedName("update_date")
    private String update_date;

    @SerializedName("username")
    private String username;

    //Crear getter y setters cuando se decidan todos los atributos
    public void setCategory(String category) {
        switch(category){
            case "National": this.category = Category.National;break;
            case "Economy": this.category = Category.Economy;break;
            case "Sports": this.category = Category.Sports;break;
            case "Technology": this.category = Category.Technology;break;
            default: this.category = null;break;
        }
    }
}
