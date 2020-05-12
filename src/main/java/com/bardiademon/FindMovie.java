package com.bardiademon;

import bardiademon.FindAllFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class FindMovie
{

    private int counter = 0;
    private final List <String> Dirs = new ArrayList <> ();

    private final Map <Integer, List <String>> Movies = new LinkedHashMap <> ();
    private AfterFound afterFound;

    public FindMovie (List <String> lstPaths , AfterFound afterFound)
    {
        this.afterFound = afterFound;
        int counter = 0;
        for (String path : lstPaths) System.out.printf ("%d.%s\n" , counter++ , path);
        System.out.print ("Select index: ");
        BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
        try
        {
            int number;
            String strNumber;
            while (true)
            {
                strNumber = reader.readLine ();
                if (strNumber.matches ("[0-9]*"))
                {
                    number = Integer.parseInt (strNumber);
                    if (number >= lstPaths.size ())
                        System.out.println ("Size list = " + lstPaths.size ());
                    else break;
                }
            }
            if (number == -1) find (lstPaths.toArray (new String[0]));
            else find (lstPaths.get (number));
        }
        catch (IOException e)
        {
            afterFound.GetMovies (null , null);
            e.printStackTrace ();
        }
    }

    public void find (String... paths)
    {
        System.out.println ();
        File[] files = toFiles (paths);
        if (files.length > 0)
        {
            new FindAllFile (new FindAllFile.CallBack ()
            {
                @Override
                public void FindTimeFile (long l , FindAllFile.Find find)
                {
                    if (Dirs.contains (find.dir.getPath ()))
                    {
                        int i = Dirs.indexOf (find.dir.getPath ());

                        List <String> list = null;

                        if (Movies.containsKey (i)) list = Movies.get (i);

                        if (list == null) list = new ArrayList <> ();

                        list.add (find.nameFile + "." + find.typeFile);

                        Movies.put (i , new ArrayList <> (list));
                        list = null;
                        System.gc ();
                    }
                }

                @Override
                public void FindTimeDir (long l , File file)
                {
                    if (!Dirs.contains (file.getPath ()))
                        Dirs.add (counter++ , file.getPath ());
                }

                @Override
                public void FindTimeFileOrDir (long l , long l1 , String s , FindAllFile.Find find , File file)
                {

                }

                @Override
                public void AfterFindFile (long l , List <FindAllFile.Find> list)
                {
                    System.out.println ("\rFound all file: " + l);
                    new Thread (() -> afterFound.GetMovies (Movies , Dirs)).start ();
                    System.gc ();
                }

                @Override
                public void AfterFindDir (long l , List <File> list)
                {

                }

                @Override
                public boolean Error (String s)
                {
                    return false;
                }
            } , files);
        }
    }

    public File[] toFiles (String[] paths)
    {
        List <File> files = new ArrayList <> ();

        File file;
        for (String path : paths)
        {
            file = new File (path);
            if (file.exists ()) files.add (file);
            else System.out.println ("Not found path => " + file);
        }
        return files.toArray (new File[0]);
    }

    public interface AfterFound
    {
        void GetMovies (Map <Integer, List <String>> Movies , List <String> Dirs);
    }
}
