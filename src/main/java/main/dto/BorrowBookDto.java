package main.dto;

public class BorrowBookDto {

    private String id;
    private String bookId;
    private String borrowerId;
    private char returned;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public char getReturned() {
        return returned;
    }

    public void setReturned(char returned) {
        this.returned = returned;
    }
}
