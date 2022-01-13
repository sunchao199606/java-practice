package cn.com.sun.http;

public class RangeSettings
{
    private long start;

    private long end;

    private long contentLength;

    private long totalLength;

    private boolean range;

    public RangeSettings()
    {
    }

    public RangeSettings(long start, long end, long contentLength,
            long totalLength)
    {
        this.start = start;
        this.end = end;
        this.contentLength = contentLength;
        this.totalLength = totalLength;
        this.range = true;
    }

    public RangeSettings(long totalLength)
    {
        this.totalLength = totalLength;
    }

    public long getStart()
    {
        return this.start;
    }

    public void setStart(long start)
    {
        this.start = start;
    }

    public long getEnd()
    {
        return this.end;
    }

    public void setEnd(long end)
    {
        this.end = end;
    }

    public long getContentLength()
    {
        return this.contentLength;
    }

    public void setContentLength(long contentLength)
    {
        this.contentLength = contentLength;
    }

    public long getTotalLength()
    {
        return this.totalLength;
    }

    public void setTotalLength(long totalLength)
    {
        this.totalLength = totalLength;
    }

    public boolean isRange()
    {
        return this.range;
    }
}
