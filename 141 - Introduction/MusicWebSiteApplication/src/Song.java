/**
 * Created by Joseph on 14/10/2016.
 * Stores a single Song, which is a composite of title and artist.
 */
public class Song {
    /* Fields */
    private String title;
    private String artist;


    /* Constructors */
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }


    /* Getters */
    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }


    /* Magic Methods */
    public String toString() {
        return this.getTitle() + " by " + this.getArtist();
    }
}
