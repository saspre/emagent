package emagent.auction;

import java.util.Stack;
import emagent.agent.brp.*;


public interface IAuction {
	public boolean addBid(IBid e) throws Exception;
	public AuctionStatus getStatus();
	public void setStatus(AuctionStatus status);
	public int getQuantity();
	public int getStartingPrice();
	public int getMinimumBidPrice();
	public IBrp getSeller();
	public Stack<IBid> getBids();
	public void close();
	public IAuctionResult getResult();
	public void newBiddingRound();
	public boolean bidAdded();
	public int getMaximumBidPrice();
	public AuctionType getAuctionType();
}