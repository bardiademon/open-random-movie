package com.bardiademon;

public class Main
{
    public static void main (String[] args)
    {
        FindList findList = new FindList ();
        if (findList.isFind ())
        {
            new FindMovie (findList.getLstPaths () , (Movies , Dirs) ->
            {
                new SelectAndOpen (Movies , Dirs);
                System.gc ();
            });
        }
        else System.out.println ("Not found list");
    }
}
