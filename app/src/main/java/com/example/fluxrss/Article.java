package tp.android.fluxrss;
public class Article {

    private String title;
    private String date;
    private String description;
    private String imageUrl;

    public Article(String title, String date, String description, String imageUrl) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}