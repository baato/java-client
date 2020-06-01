package com.baato.baatolibrary.models;

import com.google.gson.annotations.SerializedName;

public class Points
{
    @SerializedName("3D")
    private String is3D;

    private String immutable;

    private String size;

    private String dimension;

    private String empty;

    public String getIs3D() {
        return is3D;
    }

    public void setIs3D(String is3D) {
        this.is3D = is3D;
    }

    public String getImmutable ()
    {
        return immutable;
    }

    public void setImmutable (String immutable)
    {
        this.immutable = immutable;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    public String getDimension ()
    {
        return dimension;
    }

    public void setDimension (String dimension)
    {
        this.dimension = dimension;
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
        return "ClassPojo [3D = "+3D+", immutable = "+immutable+", size = "+size+", dimension = "+dimension+", empty = "+empty+"]";
    }
}