package com.example.bank;

public class Results {
    private int fieldCount;
    private int affectedRows;
    private int insertId;
    private int serverStatus;
    private int warningCount;
    private String message;
    private Boolean protocol41;
    private int changedRows;

    public Results(int fieldCount, int affectedRows, int insertId, int serverStatus, int warningCount, String message, Boolean protocol41, int changedRows) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.serverStatus = serverStatus;
        this.warningCount = warningCount;
        this.message = message;
        this.protocol41 = protocol41;
        this.changedRows = changedRows;
    }

    public int getInsertId() {
        return insertId;
    }

    public void setInsertId(int insertId) {
        this.insertId = insertId;
    }
}
