package com.kathmandulivinglabs.baatolibrary.models;

public class Annotation
{
    private String importance;

    private String message;

    private String empty;

    public String getImportance ()
    {
        return importance;
    }

    public void setImportance (String importance)
    {
        this.importance = importance;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getEmpty ()
    {
        return empty;
    }

    public void setEmpty (String empty)
    {
        this.empty = empty;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [importance = "+importance+", message = "+message+", empty = "+empty+"]";
    }
}
