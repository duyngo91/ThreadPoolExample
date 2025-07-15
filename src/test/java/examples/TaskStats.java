package examples;


import java.util.concurrent.atomic.AtomicInteger;

public class TaskStats {
    private final AtomicInteger successCount = new AtomicInteger();
    private final AtomicInteger failCount = new AtomicInteger();

    public void incrementSuccess() {
        successCount.incrementAndGet();
    }

    public void incrementFail() {
        failCount.incrementAndGet();
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getFailCount() {
        return failCount.get();
    }

    public int getTotal() {
        return successCount.get() + failCount.get();
    }

    public String getSummary() {
        StringBuilder builder = new StringBuilder();
        builder.append("✅ Thành công: " + getSuccessCount());
        builder.append("❌ Thất bại: " + getFailCount());
        builder.append("📦 Tổng: " + getTotal());
        return builder.toString();
    }

    public void printSummary() {
        System.out.println("\n📊 Task Summary:");
        System.out.println("✅ Thành công: " + getSuccessCount());
        System.out.println("❌ Thất bại: " + getFailCount());
        System.out.println("📦 Tổng: " + getTotal());
    }
}

