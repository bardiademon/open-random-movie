package com.bardiademon;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.io.FilenameUtils;

public final class SelectAndOpen extends Thread implements Runnable
{
    private final Map <Integer, List <String>> movie;
    private final List <String> dirs;

    public SelectAndOpen (Map <Integer, List <String>> movie , List <String> dirs)
    {
        this.movie = movie;
        this.dirs = dirs;
        start ();
    }

    @Override
    public void run ()
    {
        final BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
        final Random random = new Random ();

        int randomDir, randomFile;
        List <String> files;
        File file;
        Desktop desktop = Desktop.getDesktop ();
        while (true)
        {
            randomDir = random.nextInt (dirs.size ());
            files = movie.get (randomDir);
            try
            {
                randomFile = random.nextInt (files.size ());
                file = new File (dirs.get (randomDir) + File.separator + files.get (randomFile));
                if (file.exists ())
                {
                    desktop.open (file);
                    System.out.println ("Open " + FilenameUtils.getBaseName (file.getName ()));
                }
                else throw new IOException ("file does not exist " + file);
            }
            catch (IOException | IllegalArgumentException | NullPointerException e)
            {
                System.out.println (e.getMessage ());
            }

            System.out.print ("press enter to");
            try
            {
                reader.readLine ();
            }
            catch (IOException e)
            {
                System.out.println (e.getMessage ());
            }
        }
    }
}
