package shehan.com.todoapp;

public class Todo {
    private String title;
    private String description;
    private boolean doWork;

    public Todo() {
    }

    public Todo(String title, String description, boolean doWork) {
        this.title = title;
        this.description = description;
        this.doWork = doWork;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDoWork() {
        return doWork;
    }

    public void setDoWork(boolean doWork) {
        this.doWork = doWork;
    }
}
