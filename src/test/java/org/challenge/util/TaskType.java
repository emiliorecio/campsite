package org.challenge.util;

public enum TaskType {
    GET(1),
    DELETE(2),
    CREATE(3),
    MODIFY(4);

    private int taskType;

    TaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskType(){
        return taskType;
    }
}
