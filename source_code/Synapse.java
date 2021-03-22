public class Synapse
{
    //features
    private double weights;
    private double deltaWeights;
    
    //accessors getters
    public double getWeight ( )
    {
        return weights; //this is really a singular value, and should have been called "weight"
    }
    public double getDeltaWeight ( )
    {
        return deltaWeights;  //this is really a singular value, and should have been called "weight"
    }
    
    //setters
    public void setDeltaWeight ( double value )
    {
        deltaWeights = value;  //this is really a singular value, and should have been called "weight"
    }
    public void setWeight ( double value )
    {
        weights = value; //this is really a singular value, and should have been called "weight"
    }
}