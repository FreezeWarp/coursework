/**
 * Created by Joseph on 14/10/2016.
 */
public class Driver {
    public static void main(String[] args) {
        PlayList pl1 = new PlayList(5);
        pl1.addSong("Watcher of the Skies", "Genesis");
        pl1.addSong("Epitaph", "King Crimson");
        pl1.addSong("Get 'Em Out By Friday", "Genesis");

        User u1 = new User("Phil Collins", pl1);
        User u2 = new User("Peter Gabriel", pl1);

        // Naturally, they share the same memory, so adding a song to one adds it to the other.
        u1.addSong("In The Air Tonight", "Phil Collins");
        u2.addSong("Biko", "Peter Gabriel");

        System.out.println(u1);
        System.out.println(u2);

        System.out.println("Songs by Genesis in Phil Collins' Favorite Songs: " + u1.artistSongCount("Genesis"));
        System.out.println("The First Song in Peter Gabriel's PlayList: " + u2.getFavoriteSongs().getSong(0).getTitle());
    }
}
