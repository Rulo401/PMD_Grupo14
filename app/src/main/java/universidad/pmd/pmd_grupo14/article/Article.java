package universidad.pmd.pmd_grupo14.article;

import java.util.Locale;

import universidad.pmd.pmd_grupo14.common.Category;

public class Article {
    private String title;
    private String subtitle;
    private Category category;
    private String resume;
    private String body;

    public Article(String title, String subtitle, String category, String resume, String body) {
        this.title = title;
        this.subtitle = subtitle;
        this.resume = resume;
        this.body = body;
        switch(category.toLowerCase(Locale.ROOT)){
            case "nacional": this.category = Category.National;break;
            case "economia": this.category = Category.Economy;break;
            case "deportes": this.category = Category.Sports;break;
            case "tecnologia": this.category = Category.Technology;break;
            default: this.category = null;break;
        }

    }
}
