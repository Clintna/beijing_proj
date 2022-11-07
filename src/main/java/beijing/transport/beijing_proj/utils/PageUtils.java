package beijing.transport.beijing_proj.utils;

import java.util.List;

/**
 * @Author jiang
 * @Version 1.0
 * 分页工具类
 */
public class PageUtils<T> {

    private long pageIndex;  //当前页码
    private long pageSize;  //页面大小
    private long totalCount; //总条数
    private long pageCount;  //总页数

    private List<T> records; //每页的数据集合

    private long numberStart = 1;//开始的页码序号
    private long numberEnd;//结束序号

    public PageUtils(long pageIndex, long pageSize, long totalCount, List<T> records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.records = records;
        this.pageCount = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);

        this.numberStart = 1;
        this.numberEnd = pageCount;

        //数学算法
        // -----------给页码序号赋值------------------
        // 一共显示10个页面 动态伸缩
        if (this.pageCount <= 10) {
            this.numberStart = 1;
            this.numberEnd = pageCount;
        } else {
            this.numberStart = pageIndex - 4;
            this.numberEnd = pageIndex + 5;
            if (numberStart < 1) {
                this.numberStart = 1;
                this.numberEnd = 10;
            } else if (numberEnd > pageCount) {
                this.numberEnd = pageCount;
                this.numberStart = pageCount - 9;
            }
        }
        // -----------偷偷的给页码序号赋值------------------
    }

    public PageUtils() {
        this.pageCount = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getNumberStart() {
        return numberStart;
    }

    public void setNumberStart(long numberStart) {
        this.numberStart = numberStart;
    }

    public long getNumberEnd() {
        return numberEnd;
    }

    public void setNumberEnd(long numberEnd) {
        this.numberEnd = numberEnd;
    }

}



