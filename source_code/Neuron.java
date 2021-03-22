import java.util.ArrayList;
import java.util.Random;

public class Neuron
{
    //features
    private double eta;
    private double alpha;
    private int neuronId;
    private int numberOfWeightsFromNextNeuron;
    private double outcome;
    private double gradient;
    private ArrayList <Synapse> weights = new ArrayList <Synapse> ( );
    
    //constructor
    public Neuron ( double eta, double alpha, int neuronId, int numberOfWeightsFromNextNeuron )
    {
        this.eta = eta;
        this.alpha = alpha;
        this.neuronId = neuronId;
        this.numberOfWeightsFromNextNeuron = numberOfWeightsFromNextNeuron;
        gradient = 0;
        
        for ( int wI = 0; wI < numberOfWeightsFromNextNeuron; wI ++ )
        {
            weights.add ( new Synapse ( ) );
            weights.get ( wI ).setWeight ( new Random ( ).nextDouble ( ) );
        }
    }
    
    //getters
    public double getOutcome ( )
    {
        return outcome;
    }    
    public double getGradient ( )
    {
        return gradient;
    }    
    public ArrayList <Synapse> getWeights  ( )
    {
        return weights;
    }  
    public double getPrimeActivation ( double value )
    {
        return 1 - Math.pow ( Math.tanh ( value ), 2 );
    }
    public double getActivation ( double value )
    {
        return Math.tanh ( value );
    }
    public double getDistributedSigma ( Layer nextLayer )
    {
        double sigma = 0;
        
        for ( int nLI = 0; nLI < nextLayer.size ( ) - 1; nLI ++ )
        {
            sigma += getWeights ( ).get ( nLI ).getWeight ( ) * nextLayer.get ( nLI ).getGradient ( );  
        }
        
        return sigma;
    }
    
    
    
    //setters
    public void setGradient ( double value ) 
    {
        gradient = value;
    }
    public void setOutcome ( double value )
    {
        outcome = value;
    }
    public void setOutcomeGradient ( int target )
    {
        double delta = target - outcome;
        
        setGradient ( getPrimeActivation ( outcome ) * delta );
    }
    public void setHiddenGradient ( Layer nextLayer )
    {
        double delta = getDistributedSigma ( nextLayer );
        
        setGradient ( getPrimeActivation ( outcome ) * delta );
    }
    public void doForwardPropagation ( Layer priorLayer )
    {
        double sigma = 0;
        
        for ( int pLI = 0; pLI < priorLayer.size ( ); pLI ++ )
            sigma += priorLayer.get ( pLI ).getOutcome ( ) * priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).getWeight ( );
            
        setOutcome ( getActivation ( sigma ) );
    }
    public void updateWeights ( Layer priorLayer )
    {
        for ( int pLI = 0; pLI < priorLayer.size ( ); pLI ++ )
        {
            double priorDeltaWeight = priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).getDeltaWeight ( );
            
            //( eta * gradient * priorNeuronOutcome ) + ( alpha * priorDeltaWeight )
            double newDeltaWeight = ( eta * getGradient ( ) * priorLayer.get ( pLI ).getOutcome ( ) ) + ( alpha * priorDeltaWeight );
            
            priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).setDeltaWeight  ( newDeltaWeight );
            priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).setWeight  (  priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).getWeight ( ) + newDeltaWeight );
        }
    }
}
