package Model;

import java.io.Serializable;
import java.util.Map;

public class WorkerStatus implements Serializable {

    private Map<String,String> filePath;
    private String status;
    private int workerId;

    public WorkerStatus(Map<String,String> filePath, String status, int workerId) {
        this.filePath = filePath;
        this.status = status;
        this.workerId = workerId;
    }

    public WorkerStatus() {
    }

    public Map<String, String> getFilePath() {
        return filePath;
    }

    public void setFilePath(Map<String, String> filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "WorkerStatus{" +
                "filePath='" + filePath + '\'' +
                ", status='" + status + '\'' +
                ", workerId=" + workerId +
                '}';
    }
}
