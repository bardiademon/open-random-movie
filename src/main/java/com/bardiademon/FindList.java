package com.bardiademon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class FindList
{
    private static final String PATH_CONFIG = System.getProperty ("user.dir") + File.separator + "config.json";
    private List <String> lstPaths;

    public FindList ()
    {
        lstPaths = getPath (getConfig ());
    }

    private JSONObject getConfig ()
    {
        try
        {
            List <String> lstLines = Files.readAllLines (new File (PATH_CONFIG).toPath ());
            StringBuilder lines = new StringBuilder ();
            for (String line : lstLines)
                lines.append (line);

            return new JSONObject (lines.toString ());
        }
        catch (IOException | JSONException e)
        {
            System.out.println ("Error read config.json => " + e.getMessage ());
            return null;
        }
    }

    private List <String> getPath (JSONObject config)
    {
        if (config == null) return null;

        try
        {
            JSONArray jsonArray = config.getJSONArray ("path_movies");
            List <Object> list = jsonArray.toList ();
            List <String> lst = new ArrayList <> ();
            for (Object s : list) lst.add (String.valueOf (s));
            return lst;
        }
        catch (Exception e)
        {
            System.out.println ("FindList [" + 60 + "] => " + e.getMessage ());
        }
        return null;
    }

    public List <String> getLstPaths ()
    {
        return lstPaths;
    }

    public boolean isFind ()
    {
        return (getLstPaths () != null);
    }
}
