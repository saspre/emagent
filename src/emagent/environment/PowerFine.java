package emagent.environment;

public class PowerFine implements IFine {
	protected int pricePerImbalance;
	protected int imbalance;
	
	public PowerFine(int amountPerImbalance, int imbalance)
	{
		this.pricePerImbalance = amountPerImbalance;
		this.imbalance = imbalance;
	}
	
	public int amount()
	{
		return (pricePerImbalance*imbalance)^2;
	}

}
