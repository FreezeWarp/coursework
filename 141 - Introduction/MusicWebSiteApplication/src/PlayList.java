/**
 * Created by Joseph on 14/10/2016.
 * Represents a PlayList, which is an array of Songs and associiated housekeeping.
 */
public class PlayList {
    /* Fields */
    private int maxNumOfSongs;//This field seems redundant.
    private int currentNumOfSongs;
    private Song[] songList;


    /* Constructors */
    public PlayList(int maxNumOfSongs) {
        this.maxNumOfSongs = maxNumOfSongs;
        this.songList = new Song[maxNumOfSongs];
    }


    /* Setters */
    public void addSong(String title, String artist) {
        this.addSong(new Song(title, artist));
    }

    public void addSong(Song song) {
        if (currentNumOfSongs < maxNumOfSongs)
            this.songList[currentNumOfSongs++] = song;
    }


    /* Getters */
    public int getCurrentNumSongs() {
        return this.currentNumOfSongs;
    }

    public Song getSong(int pos) {
        if (pos < 0 || pos >= currentNumOfSongs)
            return null;

        return this.songList[pos];
    }

    public int getSongPosition(String title) {
        for (int i = 0; i < currentNumOfSongs; i++) {
            System.out.println(this.songList[i].getTitle());
            if (this.songList[i].getTitle() == title) return i;
        }

        return -1; // It's funny how standard -1 is for "not found."
    }


    /* Magic Methods */
    public String toString() {
        String output = "";

        for (int i = 0; i < this.getCurrentNumSongs(); i++)
            output += this.getSong(i) + "\n";

        return output;
    }
}
