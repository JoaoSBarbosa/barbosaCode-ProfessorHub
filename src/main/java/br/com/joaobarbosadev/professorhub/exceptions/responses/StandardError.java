package br.com.joaobarbosadev.professorhub.exceptions.responses;

public class StandardError {

    private String title;
    private String message;
    private Integer status;
    private String timestamp;
    private String path;


    public StandardError(){}

    public StandardError(String message, Integer status, String timestamp, String path, String title) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
