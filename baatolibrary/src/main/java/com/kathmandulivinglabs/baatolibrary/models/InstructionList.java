package com.kathmandulivinglabs.baatolibrary.models;

import java.util.HashMap;
import java.util.Map;

public class InstructionList
{
    private Annotation annotation;

    private String distance;

    private String sign;

    private String name;

    private String length;

    private String time;

    protected Map<String, Object> extraInfo = new HashMap(3);

    private Points points;

    public Annotation getAnnotation ()
    {
        return annotation;
    }

    public void setAnnotation (Annotation annotation)
    {
        this.annotation = annotation;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getSign ()
    {
        return sign;
    }

    public void setSign (String sign)
    {
        this.sign = sign;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLength ()
    {
        return length;
    }

    public void setLength (String length)
    {
        this.length = length;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public Map<String, Object> getExtraInfoJSON() {
        return this.extraInfo;
    }

    public void setExtraInfo(String key, Object value) {
        this.extraInfo.put(key, value);
    }

    public Points getPoints ()
    {
        return points;
    }

    public void setPoints (Points points)
    {
        this.points = points;
    }

    @Override
    public String toString() {
        return "InstructionList{" +
                "annotation=" + annotation +
                ", distance='" + distance + '\'' +
                ", sign='" + sign + '\'' +
                ", name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", time='" + time + '\'' +
                ", extraInfo=" + extraInfo +
                ", points=" + points +
                '}';
    }
}
