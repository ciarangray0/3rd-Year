import exceptions.InvalidTimestampException;

import java.time.LocalDateTime;

public class ForumPost {
    public static int NEXT_POST_ID = 0;
    private String postTitle;
    private String postText;
    private LocalDateTime timestamp;
    private int postID;
    private int authorId;
    public ForumPost(String postTitle, String postText, int authorId) {
        init(postTitle, postText, authorId);
        timestamp = LocalDateTime.now();
    }
    public ForumPost(LocalDateTime timestamp, String postTitle, String postText,
                     int authorId) throws InvalidTimestampException {
        init(postTitle, postText, authorId);
        if(isValid(timestamp))
            this.timestamp = timestamp;
        else
            throw new InvalidTimestampException("Timestamp cannot be in the past.");
    }
    private void init(String postTitle, String postText, int authorId) {
        this.postTitle = postTitle;
        this.postText = postText;
        postID = NEXT_POST_ID;
        NEXT_POST_ID++;
        this.authorId = authorId;
    }
    private boolean isValid(LocalDateTime timestamp) {
        if(timestamp.isBefore(LocalDateTime.now()) ||
                timestamp.isEqual(LocalDateTime.now()))
            return false;
        return true;
    }
    public Object getTitle() {
        return postTitle;
    }
    public Object getText() {
        return postText;
    }
    public Object getTimestamp() {
        return timestamp;
    }
    public void editTitle(String newTitle) {
        this.postTitle = newTitle;
    }
    public void editText(String newText) {
        this.postText = newText;
    }
    @Override
    public String toString() {
        return String.format("Timestamp: %s\nTitle: %s\nText: %s",
                timestamp.toString(), this.postTitle, this.postText);
    }
    public int getPostID() {
        return this.postID;
    }
    public int getUserID() {
        return authorId;
    }
}