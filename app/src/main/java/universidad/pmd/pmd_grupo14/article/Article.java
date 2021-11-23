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
            case "nacional": this.category = Category.Nacional;break;
            case "economia": this.category = Category.Economía;break;
            case "deportes": this.category = Category.Deportes;break;
            case "tecnologia": this.category = Category.Tecnología;break;
            default: this.category = null;break;
        }

    }
}
