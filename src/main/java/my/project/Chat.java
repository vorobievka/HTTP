package my.project;

public class Chat {

    public String id;
    public String text;
    public String cat;
    public String user;
    public int upvotes;


    public Chat() {
        // Пустой конструктор
    }

    public Chat(String id, String text, String user, int upvotes) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.upvotes = upvotes;
    }

    public String toString() {
        return "Chat " + "{" + "id=" + id + ", " + "text='" + text + "', " + "user='" + user + "', " + "upvotes=" + upvotes + "}";
    }

    public int getUpvotes() {
        return upvotes;
    }
}
