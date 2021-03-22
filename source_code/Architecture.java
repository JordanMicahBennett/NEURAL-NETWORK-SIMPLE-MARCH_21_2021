import java.util.ArrayList;

public class Architecture extends ArrayList <Integer>
{
    //feature
    private String description;
    
    public Architecture ( String description )
    {
        String parts [ ] = description.split ( "," );
        
        for ( int pI = 0; pI < parts.length; pI ++ )
            add ( Integer.parseInt ( parts [ pI ] )  );
    }
}