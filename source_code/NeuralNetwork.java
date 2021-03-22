public class NeuralNetwork
{
    //features
    private double eta = 0.2;
    private double alpha = .5;
    private Layers layers = new Layers ( );
    private Architecture architecture = new Architecture ( "2,2,1" );
    
    public NeuralNetwork ( )
    {
        for ( int lSI = 0; lSI < architecture.size ( ); lSI ++ )
        {
            layers.add ( new Layer ( ) );
            
            for ( int lI = 0; lI <= architecture.get ( lSI ); lI ++ )
            {
                int numberOfWeightsFromNextNeuron = lSI + 1 < architecture.size ( ) ? architecture.get ( lSI + 1 ) : 0;
                
                layers.get ( lSI ).add ( new Neuron ( eta, alpha, lI, numberOfWeightsFromNextNeuron ) );
             
                layers.get ( lSI ).get ( layers.get ( lSI ).size ( ) - 1 ).setOutcome ( 1.0 );
            }
        }
    }
    
    
    //do forward propagation
    public void doForwardPropagation ( int [] inputs ) 
    {
        for ( int iI = 0; iI < inputs.length; iI ++ )
        {
            layers.get ( 0 ).get ( iI ).setOutcome ( inputs [ iI ] );
        }
        
        for ( int lSI = 1; lSI < architecture.size ( ); lSI ++ )
        {
            Layer priorLayer = layers.get ( lSI - 1 );
            
            for ( int lI = 0; lI < architecture.get ( lSI ); lI ++ )
                layers.get ( lSI ).get ( lI ).doForwardPropagation ( priorLayer );
        }
    }
    
    //do backward propagation
    public void doBackwardPropagation ( int target )
    {
        ///set outcome gradient
        Neuron firstNeuronFromLastLayer = layers.get ( layers.size ( ) - 1 ).get ( 0 );
        firstNeuronFromLastLayer.setOutcomeGradient ( target );
        
        ///set hidden gradients
        for ( int lSI = architecture.size ( ) - 2; lSI > 0; lSI -- )
        {
            Layer currentLayer = layers.get ( lSI );
            Layer nextLayer = layers.get ( lSI + 1 );
            
            for ( int lI = 0; lI < currentLayer.size ( ); lI ++ )
                currentLayer.get ( lI ).setHiddenGradient ( nextLayer );
        }
        
        //update weights
        for ( int lSI = architecture.size ( ) - 1; lSI > 0; lSI -- )
        {
            Layer currentLayer = layers.get ( lSI );
            Layer priorLayer = layers.get ( lSI - 1 );
            
            for ( int lI = 0; lI < currentLayer.size ( ) - 1; lI ++ )
                currentLayer.get ( lI ).updateWeights ( priorLayer );
        }
    }
    
    //get outcome of neural network
    public double getOutcome ( )
    {
        return layers.get ( layers.size ( ) - 1 ).get ( 0 ).getOutcome ( );
    }
}
