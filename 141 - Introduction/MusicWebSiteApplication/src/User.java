/**
 * Created by Joseph on 14/10/2016.
 * Represents a User, who has a name and an associated PlayList of favorite songs.
 */
public class User {
    /* Fields */
    private String name;
    private PlayList favoriteSongs;


    /* Constructors */
    public User(String name, PlayList favoriteSongs) {
        this(name);
        this.setFavoriteSongs(favoriteSongs);
    }

    public User(String name) {
        this.name = name;
    }


    /* Setters */
    public void setFavoriteSongs(PlayList list) {
        this.favoriteSongs = list;
    }

    public void addSong(String title, String artist) {
        this.favoriteSongs.addSong(title, artist);
    }


    /* Getters */
    public PlayList getFavoriteSongs() {
        return this.favoriteSongs;
    }

    public Song getSong(String title) {
        int songPos = this.favoriteSongs.getSongPosition(title);

        if (songPos == -1)
            return null;
        else
            return this.favoriteSongs.getSong(this.favoriteSongs.getSongPosition(title));
    }

    /* For the record, this should absolutely be part of PlayList, not User, but I'm just following spec. */
    public int artistSongCount(String artist) {
        int artistSum = 0;

        for (int i = 0; i < this.favoriteSongs.getCurrentNumSongs(); i++) {
            if (this.favoriteSongs.getSong(i).getArtist() == artist) artistSum++;
        }

        return artistSum;
    }


    /* Magic Methods */
    public String toString() {
        return this.name + "'s Favorite Songs:" + "\n" + this.getFavoriteSongs().toString();
    }
}
