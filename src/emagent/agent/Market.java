package emagent.agent;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import emagent.agent.brp.IBrp;
import emagent.auction.AuctionList;
import emagent.auction.AuctionLog;
import emagent.auction.AuctionStatus;
import emagent.auction.AuctionType;
import emagent.auction.IAuction;
import emagent.auction.IAuctionResult;
import emagent.auction.NotSoldResult;
import emagent.environment.Environment;

public abstract class Market extends AbstractAgent implements IMarket {
	protected ArrayList<IBrp> auctionListeners;
	protected AuctionLog auctionLog;
	public Market()
	{
		auctionListeners = new ArrayList<IBrp>();
		auctionLog = new AuctionLog();
	}
	
	public void subscribeToAuctions(IBrp l) {
		auctionListeners.add( l);
	}
	
	public  void unsubscribeToAuctions(IBrp l) {
		auctionListeners.remove( l);
	}

	@Override
	public Collection<IBrp> getAuctionListeners()
	{
		ArrayList<IBrp> res = new ArrayList<IBrp>();
		res.addAll(auctionListeners);
		return res;
	}
	
	

	public Object getAuctioneers() {
		return auctionListeners.clone();
	}
	
	@Override
	public void startRound() throws Exception
	{
		shuffle();
		AuctionList auctions = postRound();
		
		shuffle(auctions);
		Collection<IAuctionResult> results = bidRound(auctions);
		auctions.sortByPricePerQuantity(false);
		auctionLog.addAllFirst(auctions); // For logging, they must be finished or else bad stuff will hape
		handoutRound(results);
		cleanUp();
		Environment.getEnvironment().turnOver();
		update();
	}

	@Override
	public void notifyTick(long newTick) throws Exception {
		//update();
	
	}

	protected final void cleanUp() {
	}

	protected void shuffle(ArrayList<IAuction> auctions)
	{
		shuffle();
		Collections.shuffle(auctions);
	}

	protected void shuffle()
	{
		Collections.shuffle(auctionListeners);
	}
	
	protected AuctionList postRound()
	{
		AuctionList auctions = new AuctionList();
		Collection<IAuction> curPost;
		for(IBrp brp : auctionListeners)
		{
			curPost = brp.notifyPostRound(getAuctionType());
			auctions.addAll(curPost);
		}
		for(IAuction a : auctions)
		{
			a.setStatus(AuctionStatus.POSTED);
		}
		return auctions;
	}
	
	protected abstract AuctionType getAuctionType();

	protected abstract Collection<IAuctionResult> bidRound(AuctionList auctions) throws Exception;
	
	protected void handoutRound(Collection<IAuctionResult> results)
	{
		for(IAuctionResult result : results)
		{
			if(!(result instanceof NotSoldResult))
			{
				result.getBuyer().notifyAuctionResult(result);
			}
			result.getSeller().notifyAuctionResult(result);
		}
	}
	
	@Override
	public AuctionLog getAuctionHistory() {
		return auctionLog;
	}
}
