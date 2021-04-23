package ua.kpi.comsys.IV8113.models;

public class Book {

    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image = null;

    public Book(String Ntitle, String Nsubtitle, String Nisbn13, String Nprice, String Nimage) {
        this.title = Ntitle;
        this.subtitle = Nsubtitle;
        this.isbn13 = Nisbn13;
        this.price = Nprice;
        if (!Nimage.isEmpty()) {
            this.image = Nimage;
        }
    }

    public Book() { }

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

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (!image.isEmpty()) {
            this.image = image;
        }
    }
}
