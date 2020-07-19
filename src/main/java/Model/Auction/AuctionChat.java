package Model.Auction;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AuctionChat {
    private Auction auction;
    private ArrayList<String> sender = new ArrayList<>();
    private ArrayList<String> message = new ArrayList<>();

    public AuctionChat(Auction auction) {
        this.auction = auction;
    }

    public void addMessage(String sender, String message) {
        this.sender.add(sender);
        this.message.add(message);
    }

    public static AuctionChat getAuctionChat(Auction auction) {
        for (Auction a : Auction.getAllAuctions()) {
            if (a.equals(auction)) {
                return a.getAuctionChat();
            }
        }
        return null;
    }

    public String getChatStringFormatted() {
        return sender.toString() + " - " + message.toString();
    }
}
